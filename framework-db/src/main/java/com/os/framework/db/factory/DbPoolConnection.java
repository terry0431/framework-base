package com.os.framework.db.factory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.os.framework.core.config.ConfigBean;
import com.os.framework.core.config.PathBean;
import com.os.framework.core.exception.DBException;
import com.os.framework.core.secret.DesBean;
import com.os.framework.core.util.PropertiesUtil;
import com.os.framework.db.mapping.AppMapping;
import com.os.framework.db.mod.app.AppMod;

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
