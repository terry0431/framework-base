package com.os.framework.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.os.framework.db.mod.app.AppMod;
import com.os.framework.db.factory.ConnectionFactory;
import com.os.framework.db.mapping.AppMapping;
import com.os.framework.core.exception.DBException;

/**
 * @author wangbo
 *
 */
public class AppDao extends Dao{
	private AppMod appMod = null;

	private String app = "";
	
	/**
	 * @param app 应用名 g_serverappname
	 */
	public AppDao(String app){
		//this.app = app;
		setApp(app);
	}

	public AppMod getAppMod() {
		return appMod;
	}

	/**
	 * 
	 * @param appName 应用名 g_serverappname
	 * author wangbo
	 * @version 1.0
	 */
	public void setApp(String appName) {
		close();
		this.app = appName;
		appMod = AppMapping.getAppMod(appName);
		dbBean = getDBBean(appMod.getDBType() );
	}
	@Override
	Connection getConn() {
		try {
			if(conn == null || conn.isClosed()){
				conn = ConnectionFactory.getInstance().currentConn(app);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return conn;
	}

}
