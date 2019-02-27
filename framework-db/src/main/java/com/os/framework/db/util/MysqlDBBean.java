package com.os.framework.db.util;

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

import core.exception.DBException;
import core.util.DateUtil;
import core.util.NumberUtil;
import com.os.framework.db.dao.Dao;
import com.os.framework.db.factory.DbPoolConnection;
import com.os.framework.db.mapping.TableInfoMapping;
import com.os.framework.db.mod.FieldMod;
import com.os.framework.db.mod.TableMod;
import java.sql.SQLException;

public class MysqlDBBean extends DBBean {

	@Override
	public PreparedStatement getPageListSql(String sql, Object[] values, Integer pnum, Integer maxsize, Dao dao) {
		PreparedStatement p = null;
		try {
			if (maxsize > 0) {
				sql = sql + " LIMIT ?,?";
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
				p.setInt(i, (pnum - 1) * maxsize);
				p.setInt(++i, maxsize);
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
			dbmap = dao.queryForMap("select sysdate() as dbtime", null);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return dbmap.get("dbtime").toString();
	}

	@Override
	public int getUsageConut(Dao dao) {
		int revalue = 0;
		try {
			List list = dao.queryForList("show full processlist ", null);
			if (list != null) {
				revalue = list.size();
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return revalue;
	}

	@Override
	public PreparedStatement insertSQL(String tableName, Map<String, Object> fieldvalue, Dao dao) {
		TableMod tableMod = TableInfoMapping.getTableMod(tableName);
		StringBuffer sql;
		StringBuffer keysql;
		StringBuffer valuesql;
		PreparedStatement p;
		String dbtype;
		String error_key = "";        //出现异常时记录当时的字段KEY
		FieldMod fieldMod;
		try {

			sql = new StringBuffer("INSERT INTO ").append(tableName).append(" ");
			keysql = new StringBuffer();
			valuesql = new StringBuffer();
			Map<String, FieldMod> fieldMods = tableMod.getFieldMods();

			for (String key : fieldvalue.keySet()) {
				error_key = key;
				fieldMod = fieldMods.get(key);
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

			p = dao.getPreparedStatementReturnKey(sql.toString());

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

		} catch (Exception e) {
			//System.out.println("key : " + log_key);
			throw new DBException("dao insertSQL error key : " + error_key, e);
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
	public Map<String, TableMod> getTables(String dbName) {
		//      String dataName = (String)JdbcUtil.dbinfo.get("dataName");
		//System.out.println("dataName" + dataName);
		if (StringUtils.isEmpty(dbName)) {
			throw new DBException("<dbName> 数据库名称为空");
		}
		Connection conn = null;
		ResultSet rs = null;
		Map<String, TableMod> tableinfos = new HashMap();
		List dbList = new ArrayList();
		List tableList = new ArrayList();
		String catalog;
		String tableName;
		try {
			//conn = ConnectionFactory.getInstance().currentConn();
			conn = DbPoolConnection.getInstance().getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
//			rs = metaData.getCatalogs();
//			while (rs.next()) {
//				dbList.add(rs.getString("TABLE_CAT"));
//			}

//			for (int i = 0; i < dbList.size(); i++) {
//				catalog = (String) dbList.get(i);
//				if (dbName.equalsIgnoreCase(catalog)) {
//					continue;
//				}
//				rs = metaData.getTables(catalog, "%", "%", new String[]{"TABLE"});
//				while (rs.next()) {
//					tableList.add(rs.getString("TABLE_NAME"));
//				}
//
//				for (int j = 0; j < tableList.size(); j++) {
//					tableName = (String) tableList.get(j);
//					tableinfos.put(tableName, getTableInfo(metaData, tableName, conn));
//				}
//			}
			rs = metaData.getTables(dbName, "%", "%", new String[]{"TABLE"});
			while (rs.next()) {
				//tableList.add(rs.getString("TABLE_NAME"));
				tableinfos.put(rs.getString("TABLE_NAME"), getTableInfo(metaData, rs.getString("TABLE_NAME"), conn));
			}

//			for (int j = 0; j < tableList.size(); j++) {
//				tableName = (String) tableList.get(j);
//				tableinfos.put(tableName, getTableInfo(metaData, tableName, conn));
//			}
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
		return 0;
	}

	@Override
	public boolean createTable(Dao dao, TableMod tmod) {
		return false;
	}
}
