package com.os.framework.db.util;

import com.os.framework.core.config.Config;
import com.os.framework.core.exception.DBException;
import com.os.framework.core.util.DateUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.os.framework.db.dao.Dao;
import com.os.framework.db.factory.DbPoolConnection;
import com.os.framework.db.mapping.TableInfoMapping;
import com.os.framework.db.mod.FieldMod;
import com.os.framework.db.mod.TableMod;
import org.apache.commons.lang3.StringUtils;

/**
 * JDBC封装抽象类 分别由MysqlDBBean OralcDBBean 等继承类分别实现 用于跨数据库
 *
 * @author WangBo
 * @version 1.0
 */
public abstract class DBBean {

	/**
	 * 分页sql
	 *
	 * @param sql	查询sql
	 * @param values 查询条件参数
	 * @param pnum	当前页
	 * @param maxsize	返回记录数
	 * @param dao
	 * @return	加工后的PreparedStatement对象
	 */
	public abstract PreparedStatement getPageListSql(String sql, Object[] values, Integer pnum, Integer maxsize, Dao dao);

	/**
	 * 获取数据库时间
	 *
	 * @param dao
	 * @return 时间格式为config/config.properties def_dbdatetime_format
	 */
	public abstract String getDBTime(Dao dao);

	/**
	 * 获取当前连接数
	 *
	 * @param dao
	 * @return 数据库连接数
	 */
	public abstract int getUsageConut(Dao dao);

	/**
	 * TODO 将数据转换为web前端展示格式 目前只实现了日期类型的转换
	 *
	 * @param datetype 数据类型
	 * @param data 数据
	 * @return
	 * @version 1.0
	 */
	public abstract String getViewValue(String datetype, Object data);

	/**
	 * 获取该数据库所有表信息
	 *
	 * @param dataName	数据库名称
	 * @return 表信息集合
	 */
	public abstract Map<String, TableMod> getTables(String dataName);

	/**
	 * 数据表是否存在
	 *
	 * @param dao
	 * @param tableName 数据表名称
	 * @return author wangbo
	 * @version 1.0
	 */
	public abstract int getTableCount(Dao dao, String tableName);

	/**
	 * 创建表
	 *
	 * @param dao
	 * @param tmod 表信息
	 * @return author wangbo
	 * @version 1.0
	 */
	public abstract boolean createTable(Dao dao, TableMod tmod);
	
	/**
	 * 获取该数据表信息
	 * @param tableName	数据表名称
	 * @return
	 */
	public TableMod getTable(String tableName) {
		Connection conn = null;
		ResultSet rs = null;
		TableMod tmod = null;
		try {
			//conn = ConnectionFactory.getInstance().currentConn();
			conn = DbPoolConnection.getInstance().getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			tmod = getTableInfo(metaData, tableName, conn);
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
		return tmod;
	}

	/**
	 * 获取表信息
	 *
	 * @param metaData
	 * @param tableName 数据表名称
	 * @param con
	 * @return
	 */
	protected TableMod getTableInfo(DatabaseMetaData metaData, String tableName, Connection con) {
		TableMod tableMod = new TableMod();
		FieldMod fieldObj;
		Map fieldmods = new HashMap();
		Statement stmt;
		tableMod.setTableName(tableName);

		try {
			//主键
			ResultSet pkSet = metaData.getPrimaryKeys("", "", tableName);
			while (pkSet.next()) {
				tableMod.setPKName(pkSet.getString("COLUMN_NAME"));
			}
			//外键
			ResultSet fkSet = metaData.getImportedKeys("", "", tableName);
			while (fkSet.next()) {
				for (int i = 1; i <= 14; i++) {
					System.out.println(i + "ip字段名称>>>>>>>" + fkSet.getString(i));
				}
//				System.out.println("IK字段名称>>>>>>>" + fkSet.getString("COLUMN_NAME"));
//				System.out.println("IK字段列号>>>>>>>" + fkSet.getString("KEY_SEQ"));
				//                tableObj.setPKName(pkSet.getString("COLUMN_NAME"));
			}

			fkSet = metaData.getExportedKeys("", "", tableName);
			while (fkSet.next()) {
//				System.out.println("ek字段名称>>>>>>>" + fkSet.getString("COLUMN_NAME"));
//				System.out.println("ek字段列号>>>>>>>" + fkSet.getString("KEY_SEQ"));
				//tableObj.setPKName(pkSet.getString("COLUMN_NAME"));
//				for (int i = 1; i <= 14; i++) {
//					System.out.println(i + "ep字段名称>>>>>>>" + fkSet.getString(i));
//				}
			}
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				fieldObj = new FieldMod();
				fieldObj.setFieldName(rsmd.getColumnName(i).toLowerCase());
				fieldObj.setFieldType(rsmd.getColumnTypeName(i));
				fieldObj.setFieldLength(rsmd.getColumnDisplaySize(i));
				fieldObj.setIsNull(rsmd.isNullable(i));

				try (ResultSet columnSet = metaData.getColumns("", "", tableName, rsmd.getColumnName(i))) {
					while (columnSet.next()) {
						fieldObj.setRemark(columnSet.getString("REMARKS"));
					}
				}
				fieldmods.put(fieldObj.getFieldName().toLowerCase(), fieldObj);
			}
			tableMod.setFieldMods(fieldmods);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return tableMod;
	}

	/**
	 *
	 * @param tableName 表名
	 * @param fieldvalue 字段 - 值
	 * @param dao
	 * @return
	 */
	public abstract PreparedStatement insertSQL(String tableName, Map<String, Object> fieldvalue, Dao dao);

	/**
	 *
	 * @param tableName 表名
	 * @param fieldvalue	要更新的字段-值
	 * @param dao
	 * @return
	 */
	public PreparedStatement updateSQL(String tableName, Map<String, Object> fieldvalue, Dao dao) {
		TableMod tableMod = TableInfoMapping.getTableMod(tableName);
		StringBuffer sql = new StringBuffer("UPDATE ").append(tableName).append(" SET ");
		StringBuffer setsql;
		PreparedStatement p = null;
		try {
			if (fieldvalue.get("id") == null || fieldvalue.get("id").toString().equals("")) {
				throw new DBException("数据表 <" + tableName + "> fieldvalue 中 id为空");
			}
			setsql = new StringBuffer();

			Map<String, FieldMod> fieldMods = tableMod.getFieldMods();
			fieldvalue.keySet().stream().filter((key) -> (!key.equals("id"))).forEach((key) -> {
				if (!setsql.toString().equals("")) {
					setsql.append(",");
				}
				setsql.append(key).append("=?");
			});
			sql.append(setsql).append(" where id = ?");

			p = dao.getPreparedStatement(sql.toString());
			FieldMod fieldMod;
			int i = 1;
			for (String key : fieldvalue.keySet()) {
				if (!key.equals("id")) {
					fieldMod = fieldMods.get(key);
					p.setObject(i, getDBValue(fieldMod, fieldvalue.get(key)));
					i++;
				}
			}
			p.setObject(i, fieldvalue.get("id"));

		} catch (SQLException e) {
			throw new DBException(e);
		}
		return p;
	}

	/**
	 *
	 * @param tableName 数据表名
	 * @param fieldvalue	字段-值
	 * @param wheresql 条件语句
	 * @param wherevalue	条件占位符值
	 * @param dao
	 * @return
	 */
	public PreparedStatement updateSQL(String tableName, Map<String, Object> fieldvalue, String wheresql, Object[] wherevalue, Dao dao) {
		TableMod tableMod = TableInfoMapping.getTableMod(tableName);
		StringBuffer sql = new StringBuffer("UPDATE ").append(tableName).append(" SET ");
		StringBuffer setsql;
		PreparedStatement p = null;
		try {
			if (StringUtils.isEmpty(wheresql)) {
				throw new DBException("数据表<" + tableName + ">过滤条件为空");
			}
//			if (fieldvalue.get("id") == null) {
//				throw new DBException("数据表 <" + tableName + "> fieldvalue 中id为空");
//			}
			setsql = new StringBuffer();
			FieldMod fieldMod;
			Map<String, FieldMod> fieldMods = tableMod.getFieldMods();
			//更新字段sql拼装
			fieldvalue.keySet().stream().filter((key) -> (!key.equals("id"))).forEach((key) -> {
				if (!setsql.toString().equals("")) {
					setsql.append(",");
				}
				setsql.append(key).append("=?");
			});
			sql.append(setsql).append(" ").append(wheresql);
			p = dao.getPreparedStatement(sql.toString());
			int i = 1;
			//更新字段
			for (String key : fieldvalue.keySet()) {
				if (!key.equals("id")) {
					fieldMod = (FieldMod) fieldMods.get(key);
					if (fieldvalue.get(key) == null) {
						throw new DBException("字段<" + key + ">的值为null");
					}
					p.setObject(i, getDBValue(fieldMod, fieldvalue.get(key)));
					i++;
				}
			}

			for (Object obj : wherevalue) {
				p.setObject(i, obj);
				i++;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return p;
	}

	/**
	 * 返回sql中该字段的值
	 *
	 * @param fieldMod	字段信息
	 * @param value	字段值
	 * @return
	 */
	public String getDBValue(FieldMod fieldMod, Object value) {
		if (fieldMod == null || value == null) {
			return "null";
		}
		try {
			String dbtype = fieldMod.getFieldType().toLowerCase();
			switch (dbtype) {
				case "char":
				case "varchar":
				case "text":
					//return "'" + value + "'";
					return value.toString();
				case "date":
					if (String.class.isInstance(value)) {
						return value.toString();
					} else if (Date.class.isInstance(value)) {
						return DateUtil.convertDateToString((Date) value, Config.getInstance().getDef_dbdate_format());
					} else if (Timestamp.class.isInstance(value)) {
						return DateUtil.convertDateToString((Timestamp) value, Config.getInstance().getDef_dbdate_format());
					} else if (long.class.isInstance(value) || Long.class.isInstance(value)) {
						return DateUtil.convertDateToString(new Date((Long) value), Config.getInstance().getDef_dbdate_format());
					} else {
						return null;
					}
				case "datetime":
					if (String.class.isInstance(value)) {
						return value.toString();
					} else if (Date.class.isInstance(value)) {
						return DateUtil.convertDateToString((Date) value, Config.getInstance().getDef_dbdatetime_format());
					} else if (Timestamp.class.isInstance(value)) {
						return DateUtil.convertDateToString((Timestamp) value, Config.getInstance().getDef_dbdatetime_format());
					} else if (long.class.isInstance(value) || Long.class.isInstance(value)) {
						return DateUtil.convertDateToString(new Date((Long) value), Config.getInstance().getDef_dbdatetime_format());
					} else {
						return null;
					}
				case "time":
					if (String.class.isInstance(value)) {
						return value.toString();
					} else if (Date.class.isInstance(value)) {
						return DateUtil.convertDateToString((Date) value, Config.getInstance().getDef_dbtime_format());
					} else if (Timestamp.class.isInstance(value)) {
						return DateUtil.convertDateToString((Timestamp) value, Config.getInstance().getDef_dbtime_format());
					} else if (long.class.isInstance(value) || Long.class.isInstance(value)) {
						return DateUtil.convertDateToString(new Date((Long) value), Config.getInstance().getDef_dbtime_format());
					} else {
						return null;
					}
				case "tinyint":
				case "smallint":
				case "mediumint":
				case "int":
                                        if ("".equals(value)) {
						return null;
					}
				case "integer":
					if ("".equals(value)) {
						return null;
					}
				case "bigint":
					if ("".equals(value)) {
						return null;
					}
				case "float":
					if ("".equals(value)) {
						return null;
					}
				case "double":
					if ("".equals(value)) {
						return null;
					}
				default:
					break;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return value.toString();
	}

}
