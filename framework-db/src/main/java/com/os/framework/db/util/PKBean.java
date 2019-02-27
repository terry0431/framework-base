package com.os.framework.db.util;

import java.util.Map;

import com.os.framework.db.dao.Dao;
import core.exception.DBException;

public abstract class PKBean {

	/**
	 * 程序自增
	 */
	public static int INCREMENTR = 1;

	/**
	 * 根据不同数据库 使用该数据库的自增策略
	 */
	public static int NATIVE = 2;

	/**
	 * 全局唯一标识
	 */
	public static int UUID = 3;

	/**
	 * 程序自定义
	 */
	public static int ASSIGNED = 4;

	public synchronized static int getIncrement(Dao dao, String tableName) {
            
            //加载所有 incncrement类型表的最大ID  tablename key / maxid value
            
		int id = 1;
		String sql = "select MAX(id) as maxid from " + tableName;
		try {
			Map map = dao.queryForMap(sql, null);
			if (map.get("maxid") != null && !map.get("maxid").equals("") && !map.get("maxid").toString().toLowerCase().equals("null")) {
				id = Integer.parseInt(dao.queryForMap(sql, null).get("maxid").toString()) + 1;
			}

		} catch (NumberFormatException e) {
			throw new DBException(e);
		}
		return id;
	}
}
