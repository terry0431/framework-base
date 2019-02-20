package com.os.framework.db.util;

import com.os.framework.core.config.Config;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.os.framework.db.dao.Dao;
import com.os.framework.db.factory.ConnectionFactory;
import com.os.framework.db.factory.DbPoolConnection;
import com.os.framework.db.mapping.TableInfoMapping;
import com.os.framework.db.mod.FieldMod;
import com.os.framework.db.mod.TableMod;
import com.os.framework.core.exception.DBException;
import com.os.framework.core.util.DateUtil;
import com.os.framework.core.util.NumberUtil;
import java.sql.SQLException;

public class OracleDBBean extends DBBean {
	
	@Override
	public PreparedStatement getPageListSql(String sql, Object[] values, Integer pnum, Integer maxsize, Dao dao) {
		PreparedStatement p = null;
		try {

			if (maxsize > 0) {
				//oracle 分页
				sql = "select * from ( select A.*,rownum RN from ( " + sql + " ) A where ROWNUM <= ? ) where RN >= ?";
			}
			p = dao.getPreparedStatement(sql);
			//如果values不为空  设置预处理占位符的值
			int i = 1;
			if (values != null && values.length > 0) {
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			if (maxsize > 0) {
				p.setInt(i, pnum * maxsize);
				p.setInt(++i, (pnum - 1) * maxsize + 1);
			}

		} catch (SQLException e) {
			throw new DBException(e);
		}
		return p;
	}
	
	@Override
	public String getDBTime(Dao dao) {
		Map dbmap = null;
		try {
			dbmap = dao.queryForMap("select   to_char(sysdate, 'YYYY-MM-DD_hh24:mi:ss ') as  dbtime from   dual", null);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return dbmap.get("dbtime").toString();
	}


	@Override
	public int getUsageConut(Dao dao) {
		int revalue = 0;
		try {
			Map map = dao.queryForMap("select count(*) as usagecount from v$session ", null);
			if (map != null) {
				revalue = (Integer) map.get("usagecount");
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return revalue;
	}



	@Override
	public PreparedStatement insertSQL(String tableName, Map<String,Object> fieldvalue, Dao dao) {
		TableMod tableMod = TableInfoMapping.getTableMod(tableName);
		StringBuffer sql ;
		StringBuffer keysql ;
		StringBuffer valuesql ;
		PreparedStatement p = null;
		String dbtype ;
		FieldMod fieldMod ;
		try {

			sql = new StringBuffer("INSERT INTO ").append(tableName).append(" ");
			keysql = new StringBuffer();
			valuesql = new StringBuffer();
			Map<String, FieldMod> fieldMods = tableMod.getFieldMods();
			

			for (String key : fieldvalue.keySet()) {
				fieldMod =  fieldMods.get(key);
				dbtype = fieldMod.getFieldType().toLowerCase();
				if (!dbtype.equals("char") && !dbtype.equals("varchar") && !dbtype.equals("text")
					&& (fieldvalue.get(key) == null || fieldvalue.get(key).toString().equals(""))) {
					continue;
				}
				if (!keysql.toString().equals("")) {
					keysql.append(",");
					valuesql.append(",");
				}
				keysql.append(key);

				//valuesql.append( getDBValue(fieldMod,fieldvalue.get(key)) );
				valuesql.append("?");
			}
			sql.append("(").append(keysql).append(") VALUES (").append(valuesql).append(")").toString();

			p = dao.getPreparedStatement(sql.toString(), new String[]{"id"});

			int i = 1;
			for (String key : fieldvalue.keySet()) {
				fieldMod = fieldMods.get(key);
				dbtype = fieldMod.getFieldType().toLowerCase();
				if (!dbtype.equals("char") && !dbtype.equals("varchar") && !dbtype.equals("text")
					&& (fieldvalue.get(key) == null || fieldvalue.get(key).toString().equals(""))) {
					continue;
				}
				p.setObject(i, getDBValue(fieldMod, fieldvalue.get(key)));
				i++;
			}

			if (Config.getInstance().getDebug()) {
				System.out.println("sql : " + sql);
				for (String key : fieldvalue.keySet()) {
					fieldMod = fieldMods.get(key);
					dbtype = fieldMod.getFieldType().toLowerCase();
					if (!dbtype.equals("char") && !dbtype.equals("varchar") && !dbtype.equals("text")
						&& (fieldvalue.get(key) == null || fieldvalue.get(key).toString().equals(""))) {
						System.out.println(key + " is null ");
						continue;
					}
					System.out.println(key + " : " + getDBValue(fieldMod, fieldvalue.get(key)));
				}
			}

		} catch (SQLException e) {
			throw new DBException(e);
		}
		return p;
	}

	

	@Override
	public String getViewValue(String dbtype, Object value) {

		if (value == null || value.toString().equals("") || value.toString().toLowerCase().equals("null")) {
			return "";
		}
		if (StringUtils.isEmpty(dbtype)) {
			return value.toString();
		}
		switch (dbtype) {
			case "date":
				return DateUtil.convertDBDateToString((Date) value);
			case "datetime":
				return DateUtil.convertDBDatetimeToString((Timestamp) value);
			case "time":
				return DateUtil.convertDBTimeToString((Timestamp) value);
			case "float":
				return NumberUtil.formatZero(NumberUtil.convertNumberToWeightString(value));
			case "double":
				return NumberUtil.formatZero(NumberUtil.convertNumberToWeightString(value));
			case "decimal":
				return NumberUtil.formatZero(value);
			default:
				return value.toString();
		}
	}

	@Override
	public Map<String, TableMod> getTables(String dataName) {
		Connection conn = null;
		ResultSet rs = null;
		Map<String, TableMod> tableinfos = new HashMap();

		List columnList = new ArrayList();
		String tableName ;
		try {
			//conn = ConnectionFactory.getInstance().currentConn();
			conn = conn = DbPoolConnection.getInstance().getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getTables("", metaData.getUserName(), "%", new String[]{"TABLE"});
			while (rs.next()) {
				columnList.add(rs.getString("TABLE_NAME"));
			}
			
			for (int j = 0; j < columnList.size(); j++) {
				tableName = columnList.get(j).toString().toLowerCase();
				tableinfos.put(tableName, getTableInfo(metaData, tableName, conn));
			}
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			}
		}
		return tableinfos;
	}
	
	@Override
	public int getTableCount(Dao dao, String tname) {
		try {
			String sql = "select count（tname）as ascount from tab where tname = ?";
			Map map = dao.queryForMap(sql, new Object[]{tname.toUpperCase()});
			if (map != null && map.get("ascount") != null) {
				return ((BigDecimal) map.get("ascount")).intValue();
			}
			return 0;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}
	@Override
	public boolean createTable(Dao dao, TableMod tmod) {
		try {
			FieldMod fmod ;
			String sql = "create table " + tmod.getTableName().toUpperCase();
			sql += "(ID NUMBER NOT NULL ENABLE ";

			for (String key : tmod.getFieldMods().keySet()) {
				fmod = tmod.getFieldMods().get(key);
				switch (fmod.getFieldType()) {
					case "varchar":
						sql += "," + fmod.getFieldName().toUpperCase() + " VARCHAR2(" + fmod.getFieldLength() + " BYTE) ";
						break;
					case "number":
						sql += "," + fmod.getFieldName().toUpperCase() + " NUMBER ";
						break;
					case "float":
						sql += "," + fmod.getFieldName().toUpperCase() + " FLOAT ";
						break;
					case "datetime":
						sql += "," + fmod.getFieldName().toUpperCase() + " DATETIME ";
						break;
					default:
						break;
				}
			}

			sql += ",CONSTRAINT \"PK_" + tmod.getPKName().toUpperCase()
				+ "_ID\" PRIMARY KEY (\"ID\") )";
			dao.execute(sql, null);
			TableInfoMapping.setTableinfo(tmod.getTableName().toUpperCase(),this);
			return true;
		} catch (Exception e) {
			throw new DBException(e);
		}

	}
}
