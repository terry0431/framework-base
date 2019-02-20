package com.os.framework.db.mapping;

import com.os.framework.core.config.Config;
import java.util.Map;

import com.os.framework.db.mod.TableMod;
import com.os.framework.db.util.DBBean;
import com.os.framework.db.util.MysqlDBBean;


public class TableInfoMapping {
	private static Map<String, TableMod> tablemap;
	/**
	 *  数据映射初始化
	 */
	public TableInfoMapping(){
		initTableInfoMapping();
	}
	/**
	 * 获取数据表模型
	 * @param tableName	数据表名称
	 * @return 
	 */
	public static TableMod getTableMod(String tableName){
		return tablemap.get(tableName);
	}
	
	public static void setTableinfo(String tableName,DBBean dbInfoBean){
		tablemap.put(tableName, dbInfoBean.getTable(tableName));
	}
	public static void initTableInfoMapping(){
		DBBean dbInfoBean = new MysqlDBBean();	//根据数据库类型返回相应实例
		tablemap = dbInfoBean.getTables(Config.getInstance().getDbname());
	}
}
