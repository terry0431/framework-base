package com.os.framework.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import core.config.Config;
import com.os.framework.db.factory.DbPoolConnection;
import com.os.framework.db.util.PKBean;
import core.exception.DBException;

/**
 * 主程序DAO
 *
 * @author wangbo
 */
public class MainDao extends Dao {
	
	//根据数据库类型获取适配器
	public MainDao() {
		dbBean = MainDao.getDBBean(Config.getInstance().getDbtype());
	}

//	static private final byte[] LOCK = new byte[0];
	
	@Override
	Connection getConn() {
		try {
			if (conn == null || conn.isClosed()) {
				//conn = ConnectionFactory.getInstance().currentConn();
				conn = DbPoolConnection.getInstance().getConnection();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return conn;
	}
	
	public int executeUpdate(ITransaction transaction) {

		int result = 0;
		try {
			beginTransaction();
			result = transaction.execute(this);
			commit();
		} catch (Exception e) {
			rollback();
			throw new DBException(e);
		} finally {
			if (!isTransaction) {
				close();
			}
		}

		return result;
	}

	/**
	 * 保存记录
	 *
	 * @param tableName	表名
	 * @param fieldvalue	字段-值
	 * @param pkTyple	PKBean.主键类型
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean saveOrUpdate(String tableName, Map fieldvalue, int pkTyple) {
		boolean revalue = false;
		try {
			Map map = this.queryForMap("select count(*) as c from " + tableName + " where id=?", new Object[]{fieldvalue.get("id")});
			if (map.get("c").toString().equals("0")) {
				this.save(tableName, fieldvalue, pkTyple);
			} else {
				this.update(tableName, fieldvalue);
			}
			revalue = true;
		} catch (Exception e) {
			throw new DBException(e);
		}
		return revalue;
	}

	/**
	 * 添加记录
	 *
	 * @param tableName	表名
	 * @param fieldvalue	字段-值
	 * @param pkTyple	PKBean.主键类型
	 * @return author wangbo
	 * @version 1.0
	 */
	public int save(String tableName, Map fieldvalue, int pkTyple) {
		int id = 0;
		ResultSet rs = null;
		PreparedStatement p = null;
		
			try {
				if (pkTyple == PKBean.INCREMENTR) {
//                                    synchronized (LOCK) {
					fieldvalue.put("id", PKBean.getIncrement(this, tableName));
//                                    }
				}
				if (pkTyple == PKBean.UUID) {
					fieldvalue.put("id", UUID.randomUUID().toString());
				}
				p = dbBean.insertSQL(tableName, fieldvalue, this);
				p.executeUpdate();
				if (pkTyple == PKBean.NATIVE) {
					rs = p.getGeneratedKeys();
					if (rs.next()) {
						id = rs.getInt(1);
					}
				} else if (pkTyple != PKBean.ASSIGNED) {
					id = (Integer) fieldvalue.get("id");
				}

			} catch (Exception e) {
				throw new DBException(e);
			} finally {

				if (!isTransaction) {
					close(rs);
					close(p);
					close();
				}
			}
			return id;
		
	}

	/**
	 * 添加记录 主键类型不支持程序自增主键
	 *
	 * @param tableName	表名
	 * @param fieldvalue	字段-值
	 * @param pkTyple	PKBean.主键类型
	 * @return author wangbo
	 * @version 1.0
	 */
	public String saveUnlock(String tableName, Map fieldvalue, int pkTyple) {
		String id = "";
		ResultSet rs = null;
		PreparedStatement p = null;
		try {
			if (pkTyple == PKBean.INCREMENTR) {
				return "-1";
			}
			if (pkTyple == PKBean.UUID) {
				fieldvalue.put("id", UUID.randomUUID().toString());
			}
			p = dbBean.insertSQL(tableName, fieldvalue, this);
			p.executeUpdate();
			if (pkTyple == PKBean.NATIVE) {
				rs = p.getGeneratedKeys();
				if (rs.next()) {
					id = String.valueOf(rs.getInt(1));
				}
			} else {
				id = String.valueOf((Integer) fieldvalue.get("id"));
			}

		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			close(rs);
			close(p);
			if (!isTransaction) {
				close();
			}
		}
		return id;
	}

	/**
	 *
	 * @param tableName	数据表名
	 * @param fieldvalue	占位符值，缺省添加条件语句 where id = ? ps.set(map.get("id"))
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean update(String tableName, Map fieldvalue) {
		boolean revalue = false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = dbBean.updateSQL(tableName, fieldvalue, this);
			preparedStatement.execute();
			revalue = true;
		} catch (Exception e) {
			revalue = false;
			throw new DBException(e);
		} finally {
			close(preparedStatement);
			if (!isTransaction) {
				close();
			}
		}
		return revalue;
	}

	/**
	 * 更新数据
	 *
	 * @param tableName	数据表名
	 * @param fieldvalue	字段-值
	 * @param wheresql	条件语句
	 * @param wherevalue	条件占位符值
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean update(String tableName, Map fieldvalue, String wheresql, Object[] wherevalue) {
		boolean revalue = false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = dbBean.updateSQL(tableName, fieldvalue, wheresql, wherevalue, this);
			preparedStatement.execute();
			revalue = true;
		} catch (Exception e) {
			revalue = false;
			throw new DBException(e);
		} finally {
			close(preparedStatement);
			if (!isTransaction) {
				close();
			}
		}
		return revalue;
	}

	/**
	 * 添加字段
	 *
	 * @param tablename 数据表名
	 * @param fieldname	字段名称
	 * @param fieldtype	字段类型
	 * @param fieldlength	字段长度
	 * @return author wangbo
	 * @version 1.0
	 */
	public boolean addColumn(String tablename, String fieldname, String fieldtype, String fieldlength) {
		String dbtype = "";
		try {
			if (fieldtype.equals("char")
				|| fieldtype.equals("varchar")
				|| fieldtype.equals("text")) {
				dbtype = fieldtype + "(" + fieldlength + ")";
			} else {
				dbtype = fieldtype;
			}
			String ddl = "ALTER TABLE " + tablename + " ADD " + fieldname + " " + dbtype;
			return this.execute(ddl, null);
		} catch (RuntimeException e) {
			throw new DBException(e);
		}
	}

	/**
	 * 批量新增的方法
	 *
	 * @param value 字段值
	 * @param tableName 表名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String BatchInsert(List<Map<String, Object>> value, String tableName, MainDao mainDao) {
		if (value == null || value.size() == 0) {
			return "0";
		}
		//int size = value.size() ;
		StringBuilder sql = new StringBuilder();
		Map<String, Object> map_columns = value.get(0);
		Set mapkeys = map_columns.keySet();
		List<String> list = new ArrayList<String>(mapkeys);
		StringBuffer columns = new StringBuffer();
		//获取列
		for (int i = 0; i < list.size(); i++) {
			columns.append(" , " + list.get(i));
		}
		sql.append("INSERT INTO " + tableName);
		sql.append("( id");
		sql.append(columns.toString());
		sql.append(") values ");
		int i = 1;
		int maxid = PKBean.getIncrement(mainDao, tableName);
		//给列赋值
		for (Map column_map : value) {

			if (i == 1) {
				sql.append(" ( " + maxid);
			} else {
				sql.append(" ,( " + maxid);
			}
			for (int j = 0; j < list.size(); j++) {
				sql.append(" , " + column_map.get(list.get(j)));//字段值
			}
			sql.append(" ) ");
			maxid++;
			i++;
		}
		System.out.println("批量插入sql: " + sql.toString());
		try {
			mainDao.execute(sql.toString(), null);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "0";
		}
	}

}
