package com.os.framework.db.factory;

import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import core.config.PathBean;
import core.util.PropertiesUtil;

public class DbPoolConnection {
	private static DbPoolConnection databasePool=null;
	private static DruidDataSource dds = null;
	static {
//		Properties properties = loadPropertyFile("db_server.properties");
		
		try {
			Properties properties = PropertiesUtil.getProp(PathBean.DBINFOPATH);
			dds = (DruidDataSource) DruidDataSourceFactory
					.createDataSource(properties);
//			dds = new DruidDataSource();
//			dds.setUrl("jdbc:mysql://127.0.0.1:3306/framework_dev");
//			dds.setDriverClassName("com.mysql.jdbc.Driver");
//			dds.setUsername("root");
//			dds.setPassword("root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private DbPoolConnection() {}
	public static synchronized DbPoolConnection getInstance() {
		if (null == databasePool) {
			databasePool = new DbPoolConnection();
		}
		return databasePool;
	}
	public DruidPooledConnection getConnection() throws SQLException {
		return dds.getConnection();
	}
	
}
