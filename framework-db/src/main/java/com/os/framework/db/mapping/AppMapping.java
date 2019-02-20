package com.os.framework.db.mapping;

import java.util.HashMap;
import java.util.Map;

import com.os.framework.db.dao.AppDao;
import com.os.framework.db.dao.Dao;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.mod.app.AppMod;

public class AppMapping {
	private static Map<String, AppMod> appsmap;
	
	public AppMapping(){
		initAppMapping();
	}
	public static AppMod getAppMod(String appName){
		return appsmap.get(appName);
	}
	
	public static Map getApps(){
		return appsmap;
	}
	
	public static void initAppMapping(){
		appsmap = new HashMap();
		AppMod appMod = null;
//		appMod = new AppMod();
//		appMod.setAppServerIp("127.0.0.1");
//		appMod.setAppServerName("main");
//		appMod.setDBDriver("com.mysql.jdbc.Driver");
//		appMod.setDBIp("127.0.0.1");
//		appMod.setDBName("portal");
//		appMod.setDBPort("3306");
//		appMod.setDBPwd("root");
//		appMod.setDBUser("root");
//		appMod.setDBType("Mysql");
//		appMod.setDBMaxPool(20);
//		appMod.setDBMaxSt(50);
//		appsmap.put(appMod.getAppServerName(), appMod);
//		System.out.println("Dao dao = new MainDao();");
		Dao dao = new MainDao();
		AppDao appDao = null;
		//System.out.println("dao.getDBTime();");
		dao.getDBTime();
		/*List<Map<String,Object>> applist = dao.queryForList("select * from gf001",null);
		
		if(applist != null && applist.size() > 0){
			
			for(Map appmap : applist){
				appMod = new AppMod();
				appMod.setId(appmap.get("id").toString());
				appMod.setOrgId(appmap.get("g_orgid").toString());
				appMod.setAppServerIp(appmap.get("g_serverip").toString());
				appMod.setAppServerAppName(appmap.get("g_serverappname").toString());
				appMod.setAppServerName(appmap.get("g_servername").toString());
				appMod.setDBDriver(appmap.get("g_dbdriver").toString());
				appMod.setDBIp(appmap.get("g_dbip").toString());
				appMod.setDBName(appmap.get("g_dbname").toString());
				appMod.setDBPort(appmap.get("g_dbport").toString());
				appMod.setDBPwd(DesBean.decrypt(appmap.get("g_dbpwd").toString()));
				appMod.setDBUser(DesBean.decrypt(appmap.get("g_dbuser").toString()));
				appMod.setDBType(appmap.get("g_dbtype").toString());
				if(appmap.get("g_dbmaxpool") != null && !"".equals(appmap.get("g_dbmaxpool"))){
					appMod.setDBMaxPool((Integer)appmap.get("g_dbmaxpool"));
				}
				if(appmap.get("g_dbmaxstatments") != null && !"".equals(appmap.get("g_dbmaxstatments"))){
					appMod.setDBMaxSt((Integer)appmap.get("g_dbmaxstatments"));
				}
				
				appsmap.put(appMod.getAppServerAppName(), appMod);
				appDao = new AppDao(appMod.getAppServerAppName());
				appDao.getDBTime();
			}
		}*/
	}
}
