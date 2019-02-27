package com.os.framework.db.factory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.os.framework.db.mod.app.AppMod;
import core.config.ConfigBean;
import core.config.PathBean;
import core.exception.DBException;
import core.secret.DesBean;
import com.os.framework.db.mapping.AppMapping;
import core.util.PropertiesUtil;
import java.util.HashMap;

/**
 * 数据连接工厂类
 *
 * @author wangbo
 *
 */
public class ConnectionFactory {

	private static ConnectionFactory single;	//单例
	//private static final byte[] LOCK = new byte[0];	//并发锁

	public static ConnectionFactory getInstance() {
		if (single == null) {
			single = new ConnectionFactory();
		}
		return single;
	}

	private static HashMap cpdsmap = new HashMap();	//数据源
	private static ComboPooledDataSource localcpds = new ComboPooledDataSource();	//平台数据源

	/**
	 *
	 * @throws Exception
	 */
	private ConnectionFactory() {
//		log.info("初始化管理平台数据源");
//		log.error("初始化管理平台数据源");
//		localcpds.setProperties(PropertiesUtil.getProp(PathBean.DBINFOPATH));	//不清楚为什么该函数不起作用
		//初始化管理平台数据源
		initComboPooledDataSource();
	}
	
	private void initComboPooledDataSource(){
		try {
			Properties properties = PropertiesUtil.getProp(PathBean.DBINFOPATH);
			localcpds.setDataSourceName(ConfigBean.MAIN);
			localcpds.setDriverClass(properties.getProperty("c3p0.driverClass"));
			localcpds.setJdbcUrl(properties.getProperty("c3p0.jdbcUrl"));
			localcpds.setUser(DesBean.decrypt(properties.getProperty("c3p0.user")));
			localcpds.setPassword(DesBean.decrypt(properties.getProperty("c3p0.password")));
			localcpds.setMaxStatements(Integer.parseInt(properties.getProperty("c3p0.maxStatements")));
			localcpds.setMaxPoolSize(Integer.parseInt(properties.getProperty("c3p0.maxPoolSize")));
//			localcpds.setCheckoutTimeout(5000);
//			localcpds.setBreakAfterAcquireFailure(false);
//			localcpds.setMaxIdleTime(10);
//			localcpds.setAcquireRetryAttempts(10);
//			localcpds.setAcquireIncrement(15);
//			localcpds.setIdleConnectionTestPeriod(30);
//			localcpds.setUnreturnedConnectionTimeout(100);
//			localcpds.setNumHelperThreads(5);

			//初始化应用数据源
//			List<AppMod> list = new ArrayList<AppMod>();
//			for(AppMod projectMod  : list){
//				cpdsmap.put(projectMod.getAppServerName(), loadComboPooledDataSource(projectMod));
//			}
		} catch (NumberFormatException e) {
			throw new DBException(e);
		} catch (PropertyVetoException e) {
			throw new DBException(e);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}
	/**
	 * 获取平台数据连接
	 *
	 * @return
	 * @throws Exception
	 */
	public Connection currentConn() throws Exception {

		return localcpds.getConnection();
	}

	/**
	 * 重新加载数据源
	 *
	 * @param appName	应用名
	 */
	public void reloadDs(String appName) {
		try {
			//log.error("===========================reloadDs !!!!!   ================================");
			if (appName.equals(ConfigBean.MAIN)) {
				if (localcpds != null) {
					localcpds.close();
				}
				localcpds = new ComboPooledDataSource();
				initComboPooledDataSource();
			} else {
				AppMod projectMod = AppMapping.getAppMod(appName);
				ComboPooledDataSource cpds = (ComboPooledDataSource) cpdsmap.get(projectMod.getAppServerName());
				cpds.close();
				//if(cpds == null){
				cpds = loadComboPooledDataSource(projectMod);
				cpdsmap.put(projectMod.getAppServerName(), cpds);
			}
			//}
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public void softReset(String appName) {
		try {
			if (appName.equals(ConfigBean.MAIN)) {
				localcpds.softResetAllUsers();
			} else {
				ComboPooledDataSource cpds = (ComboPooledDataSource) cpdsmap.get(appName);
				cpds.softResetAllUsers();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * 根据应用服务名获取应用服务对象
	 *
	 * @param appName
	 * @return
	 * @throws Exception
	 */
	public Connection currentConn(String appName) throws Exception {
		AppMod projectMod = AppMapping.getAppMod(appName);
		ComboPooledDataSource cpds = (ComboPooledDataSource) cpdsmap.get(projectMod.getAppServerName());
		try {
			//加载新添加的数据源
			if (cpds == null) {
				cpds = loadComboPooledDataSource(projectMod);
				cpdsmap.put(projectMod.getAppServerName(), cpds);
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return cpds.getConnection();
	}

	/**
	 * 加载数据源
	 *
	 * @param appMod	应用程序描述
	 * @return
	 * @throws Exception
	 */
	private ComboPooledDataSource loadComboPooledDataSource(AppMod appMod)  {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDataSourceName(appMod.getAppServerName());
			cpds.setDriverClass(appMod.getDBDriver());
			if (appMod.getDBType().equals(ConfigBean.MYSQL)) {
				cpds.setJdbcUrl("jdbc:mysql://" + appMod.getDBIp() + ":" + appMod.getDBPort() + "/" + appMod.getDBName() + "?zeroDateTimeBehavior=convertToNull");
			} else if (appMod.getDBType().equals(ConfigBean.MSSQL)) {

			} else if (appMod.getDBType().equals(ConfigBean.ORACLE)) {

			}
			cpds.setUser(appMod.getDBUser());
			cpds.setPassword(appMod.getDBPwd());
			cpds.setMaxStatements(appMod.getDBMaxSt());
			cpds.setMaxPoolSize(appMod.getDBMaxPool());
		} catch (PropertyVetoException e) {
			throw new DBException(e);
		}
		return cpds;
	}

}
