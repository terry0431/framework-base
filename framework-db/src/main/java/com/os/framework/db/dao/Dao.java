package com.os.framework.db.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import oracle.sql.CLOB;
import com.os.framework.core.exception.DBException;
import com.os.framework.core.secret.Des;
import com.os.framework.db.mod.TableMod;
import com.os.framework.db.util.DBBean;

import java.sql.ParameterMetaData;

/**
 * 数据处理类
 * TODO
 * 之后要实现加密字段的自动处理
 * 1，服务启动时加载被加密的表与字段信息到内存
 * 2，每次查询、储存时 检查当前表是否含有加密表 如含有读取表相关的加密字段，进行自动加密解密
 * @author wangbo
 *
 */
public abstract class Dao {

	Connection conn;
	boolean isTransaction = false;

	DBBean dbBean = null;

	abstract Connection getConn();
	

	/**
	 * 关闭 ResultSet
	 * @param o 
	 */
	void close(ResultSet o) {
		try {
			if (o != null) {
				o.close();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 关闭Statement
	 * @param o 
	 */
	void close(Statement o) {
		try {
			if (o != null) {
				o.close();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 关闭PreparedStatement
	 * @param o 
	 */
	void close(PreparedStatement o) {
		try {
			if (o != null) {
				o.close();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 关闭当前数据库连接
	 */
	void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			conn = null;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 获取PreparedStatement
	 * @param sql
	 * @return 
	 */
	public PreparedStatement getPreparedStatement(String sql) {
		try {
			PreparedStatement p = getConn().prepareStatement(sql);
			return p;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 获取PreparedStatement
	 * @param sql
	 * @return 
	 */
	public PreparedStatement getPreparedStatementReturnKey(String sql) {
		try {
			return getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 获取PreparedStatement
	 * @param sql
	 * @param columnNames
	 * @return 
	 */
	public PreparedStatement getPreparedStatement(String sql, String columnNames[]) {
		try {
			return getConn().prepareStatement(sql, columnNames);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * 开启事务
	 *
	 *  lv (1,2,4,8) 暂时默认强制为2 ,给入的参数无效,无特殊需求请传入参数为2 TRANSACTION_NONE
	 * 不支持事务 1 TRANSACTION_READ_UNCOMMITTED 允许脏读取、不可重复的读和虚读。 2
	 * TRANSACTION_READ_COMMITTED 读取未提交的数据是不允许的。允许不可重复的读和虚读。 4
	 * TRANSACTION_REPEATABLE_READ 事务保证能够再次读取相同的数据而不会失败，但虚读仍会出现。 8
	 * TRANSACTION_SERIALIZABLE 防止脏读、不可重复的读和虚读。(串行,禁止并发,严重影响性能)
	 */
	public void beginTransaction() {
		try {
			isTransaction = true;
			if (conn == null) {
				getConn();
			}
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * 开启事物
	 */
	public void commit() {

		try {
			if (conn != null && isTransaction) {
				conn.commit();
			}
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close();
		}
	}
	/**
	 * 回滚事务
	 */
	public void rollback() {
		try {
			if (conn != null && isTransaction) {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close();
		}
	}

	/**
	 * 获取数据库时间
	 * @return 时间格式为config/config.properties def_dbdatetime_format
	 */
	public String getDBTime() {
		String date = "";
		try {
			date = dbBean.getDBTime(this);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
		}
		return date;
	}

	/**\
	 * 获取记录数
	 * @param tname 数据表名称
	 * @return
	 */
	public int getTableCount(String tname) {
		try {
			return dbBean.getTableCount(this, tname);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
		}
	}

	/**
	 * 创建数据表
	 * @param tmod	数据表模型
	 * @return
	 */
	public boolean createTable(TableMod tmod) {
		try {
			return dbBean.createTable(this, tmod);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
		}
	}

	/**
	 * 获取当前连接数
	 *
	 * @return 
	 */
	public int getUsageConut() {
		int revalue = 0;
		try {
			revalue = dbBean.getUsageConut(this);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
		}
		return revalue;
	}

	/**
	 * SQL查询  返回单条(第一条)结果集 
	 * @param sql
	 * @param objs
	 * @return 
	 */
	public HashMap<String, Object> queryForMap(String sql, Object[] objs) {
		HashMap<String, Object> map = new HashMap();
		ResultSet rs = null;
		PreparedStatement p = null;
		try {
			p = getConn().prepareStatement(sql);
			
			ParameterMetaData pmd = p.getParameterMetaData() ;
			int parameterCount = pmd.getParameterCount() ;
			if (parameterCount > 0 && ( objs == null || objs.length !=  parameterCount) ) {
				throw new DBException("参数个数与sql参数化占位符个数不匹配");
			}

			if (objs != null && objs.length > 0) {
				int i = 1;
				for (Object obj : objs) {
					p.setObject(i, obj);
					i++;
				}
			}

			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			if (rs.next()) {
				int colCount = metaData.getColumnCount();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel (j + 1).toLowerCase();
					Object colValue = rs.getObject(j + 1);
					if (colValue == null || "".equals(colValue) || "null".equals(colValue.toString().toLowerCase())) {
						colValue = "";
					}
					map.put(colName.toLowerCase(), colValue);
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (! isTransaction) {
				close();
			}
		}
		return map;
	}

	/**
	 * SQL查询 将指定类型转换为适合显示的格式，目前只支持日期类型的转换
	 * @param sql
	 * @param objs
	 * @return 
	 */
	public HashMap<String, Object> queryForViewMap(String sql, Object[] objs) {
		HashMap<String, Object> map = new HashMap();
		ResultSet rs = null;
		PreparedStatement p = null;
		try {
			p = getConn().prepareStatement(sql);

			if (objs != null && objs.length > 0) {
				int i = 1;
				for (Object obj : objs) {
					p.setObject(i, obj);
					i++;
				}
			}
			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			if (rs.next()) {
				int colCount = metaData.getColumnCount();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					String dbtype = metaData.getColumnTypeName(j + 1).toLowerCase();
					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col.toString().toLowerCase())) {
						col = "";
					}
					map.put(colName, dbBean.getViewValue(dbtype, col));
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return map;
	}

	
	/**
	 *SQL 查询 支持分页 
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @param pnum	当前页
	 * @param maxrs	返回记录数
	 * @return 
	 */
	public List<Map<String, Object>> queryForList(String sql, Object[] values, int pnum, int maxrs) {
		List<Map<String, Object>> list = null;
		ResultSet rs = null;
		PreparedStatement p = null;
		Map<String, Object> map ;
		try {
			p = dbBean.getPageListSql(sql, values, pnum, maxrs, this);
			rs = p.executeQuery();
			list = new ArrayList();
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				map = new HashMap();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col.toString().toLowerCase())) {
						col = "";
					}
					//map.put(colName.toLowerCase(), col);
					map.put(colName, col);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return list;
	}

	
	/**
	 * 查询显示数据列表，自动转换日期格式，以后实现外键关联获取外键显示值
	 *
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @return 
	 */
	public List<Map<String, Object>> queryForViewList(String sql, Object[] values) {
		List<Map<String, Object>> list = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		Map<String, Object> map ;
		try {
			p = getConn().prepareStatement(sql);
			if (values != null && values.length > 0) {
				int i = 1;
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			list = new ArrayList();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				map = new HashMap();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					String dbtype = metaData.getColumnTypeName(j + 1).toLowerCase();
					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col) || "Null".equals(col)) {
						col = "";
					}
					map.put(colName, dbBean.getViewValue(dbtype, col));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return list;
	}

	

	/**
	 * 查询显示数据列表，自动转换日期格式，以后实现外键关联获取外键显示值
	 *
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @param pnum	当前页
	 * @param maxrs	返回记录数
	 * @return author wangbo
	 * @version 1.0
	 */
	public List<Map<String, Object>> queryForViewList(String sql, Object[] values, int pnum, int maxrs) {
		List<Map<String, Object>> list = null;
		ResultSet rs = null;
		PreparedStatement p = null;
		try {
			p = dbBean.getPageListSql(sql, values, pnum, maxrs, this);
			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			list = new ArrayList();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					String dbtype = metaData.getColumnTypeName(j + 1).toLowerCase();

					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col.toString().toLowerCase())) {
						col = "";
					}
					//map.put(colName.toLowerCase(), col);
					//map.put(colName, col);
					map.put(colName, dbBean.getViewValue(dbtype, col));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return list;
	}

	

	/**
	 * 查询显示数据列表，自动转换日期格式，以后实现外键关联获取外键显示值
	 *
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @return author wangbo
	 * @version 1.0
	 */
	public List<Map<String, Object>> queryForViewLinkedList(String sql, Object[] values) {
		List<Map<String, Object>> list = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		Map<String, Object> map ;
		try {
			p = getConn().prepareStatement(sql);
			if (values != null && values.length > 0) {
				int i = 1;
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			list = new ArrayList();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				map = new LinkedHashMap();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					String dbtype = metaData.getColumnTypeName(j + 1).toLowerCase();
					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col) || "Null".equals(col)) {
						col = "";
					}
					map.put(colName, dbBean.getViewValue(dbtype, col));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return list;
	}

	

	

	/**
	 *
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @return author wangbo
	 * @version 1.0
	 */
	public List<Map<String, Object>> queryForList(String sql, Object[] values) {
		List<Map<String, Object>> list = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = getConn().prepareStatement(sql);
			if (values != null && values.length > 0) {
				int i = 1;
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			rs = p.executeQuery();
			list = new ArrayList();

			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap();
				for (int j = 0; j < colCount; j++) {
					String colName = metaData.getColumnLabel(j + 1).toLowerCase();
					Object col = rs.getObject(j + 1);
					if (col == null || "".equals(col) || "null".equals(col) || "Null".equals(col)) {
						col = "";
					}
					map.put(colName, col);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
		}
		return list;
	}

	

	/**
	 *
	 * @param sql	查询语句
	 * @param values	替换占位符"?"的值，若没有条件查询可以传入null
	 * @param filepath	生成文件的路径
	 * @param isEncode	是否对数据进行加密
	 * @return 
	 */
	public boolean queryForFile(String sql, Object[] values, String filepath,boolean isEncode) {
		List<Map<String, Object>> list = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		StringBuffer s = new StringBuffer();

		File toFile ;
		FileWriter filewriter = null;
		BufferedWriter bufferedwriter = null;
		PrintWriter printwriter = null;
		Des des = new Des();
		String colvalue = "";
		try {
	
			p = getConn().prepareStatement(sql);
			if (values != null && values.length > 0) {
				int i = 1;
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			rs = p.executeQuery();
			list = new ArrayList();
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			int i = 1;
			//写入文件准备
			toFile = new File(filepath);
			//if(charset.equals("def")){
			filewriter = new FileWriter(toFile);
			bufferedwriter = new BufferedWriter(filewriter);
			//charset = "Unicode";
			//}else{
			//	pageHtmlBw = new BufferedWriter(new   OutputStreamWriter(new   FileOutputStream( toFile),   charset));
			//}
			printwriter = new PrintWriter(bufferedwriter);

			while (rs.next()) {

				for (int j = 0; j < colCount; j++) {
					Object col = rs.getObject(j + 1);
					String coltype = metaData.getColumnTypeName(j + 1).toLowerCase();

					if (col == null || "".equals(col) || "null".equals(col) || "Null".equals(col)) {
						col = "";
					}
					
//					if (coltype.equals("clob")) {
//						col = ClobToString((CLOB) col);
//						if(isEncode){
//							col = des.encrypt(col.toString() );
//						}
//						s.append(des.encrypt(metaData.getColumnLabel(j + 1)) )
//							.append(":")
//							.append( col )
//							.append("|");
//					} 
                                        else {
						if(isEncode){
							col = des.encrypt(col.toString() );
						}
						s.append(des.encrypt(metaData.getColumnLabel(j + 1)))
							.append(":")
							.append( col )
							.append("|");
					}
				}
				s.append("\r\n");
				if (s.length() > 10000) {
					bufferedwriter.write(s.toString());
					s = new StringBuffer();
				}
				i++;
			}
			if (s.length() > 0) {
				bufferedwriter.write(s.toString());
			}

		} catch (IOException | SQLException e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {

				close();
			}
			if (bufferedwriter != null) {
				try {
					bufferedwriter.close();
				} catch (IOException e) {
					throw new DBException(e);
				}
			}
			if (printwriter != null) {
				printwriter.close();
			}
			if (filewriter != null) {
				try {
					filewriter.close();
				} catch (IOException e) {
					throw new DBException(e);
				}
			}
		}
		return true;
	}

	/**
	 *
	 * @param sql	执行sql
	 * @param values	占位符替换值，没有"?"占位符可传入null
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean execute(String sql, Object[] values) {
		boolean returnVal = false;
		PreparedStatement p = null;
		try {
			p = getConn().prepareStatement(sql);
			int i = 1;
			if (values != null && values.length > 0) {
				for (Object obj : values) {
					p.setObject(i, obj);
					i++;
				}
			}
			p.execute();
			returnVal = true;
			
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			close(p);
			if (!isTransaction) {
				close();
			}
		}
		return returnVal;
	}

	/**
	 * 执行存储过程
	 *
	 * @param callable 存储过程语句
	 * @param values	存储过程参数值
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean executeCallable(String callable, Object[] values) {
		boolean returnVal = false;
		try {

			String args = "";
			if (values != null && values.length > 0) {
				for (Object value : values) {
					if (!args.equals("")) {
						args += ",";
					}
					args += "?";
				}
			}
			CallableStatement cstmt = getConn().prepareCall("{ call " + callable + "(" + args + ")}");
			if (values != null && values.length > 0) {
				int j = 1;
				for (Object obj : values) {
					cstmt.setObject(j, obj);
					j++;
				}
			}

			cstmt.execute();
			returnVal = true;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
			
		}
		return returnVal;
	}

	

	/**
	 * 将字CLOB转成STRING类型
	 *
	 * @ param CLOB
	 * @return String author yuq
	 * @version 1.0
	 */
//	private static String ClobToString(CLOB clob) throws SQLException {
//		String reString = "";
//		try {
//			Reader is = clob.getCharacterStream();// 得到流  
//			BufferedReader br = new BufferedReader(is);
//			String s = br.readLine();
//			StringBuilder sb = new StringBuilder();
//			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING  
//				sb.append(s);
//				s = br.readLine();
//			}
//			reString = sb.toString();
//		} catch (IOException | SQLException e) {
//			throw new DBException(e);
//		} 
//		return reString;
//	}

	public static DBBean getDBBean(String appDBType) {
		try {
			String className = DBBean.class.getName();
			className = className.replaceAll("DBBean", appDBType + "DBBean");
			Class c = Class.forName(className);
			return (DBBean) c.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new DBException(e);
		}

	}
}
