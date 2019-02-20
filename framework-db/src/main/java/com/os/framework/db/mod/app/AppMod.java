package com.os.framework.db.mod.app;

public class AppMod {
	private String id;				//主键id
	private String orgId;				//机构id
	private String appServerName;			//应用服务名
	private String appViewName;			//显示名称
	private String appServerIp;			//应用服务ip
	private String appServerPort;			//应用服务端口
	private String appServerAppName;		//应用名
	private String appServerUrl;			//服务地址
	private String DBName;			//数据库名称
	private String DBIp;				//数据库服务地址
	private String DBPort;			//数据库服务端口
	private String DBType;			//数据库类型
	private String DBUser;			//数据库账户
	private String DBPwd;			//数据库密码
	private String DBDriver;			//数据库驱动
	private int DBMaxSt;				//最大缓存statements数
	private int DBMaxPool;			//连接池中保留的最大连接数
	private int DBminPool;			//连接池中保留的最小连接数
	private String DBAutomatic;			//检查数据连接用表
	private int DBCkoutTimeout;			//等待连接超时时间
	private int DBConnTestPeriod;			//检查空闲连接时间间隔
	private int DBInitialPool;			//初始化连接数
	private int DBMaxIdleTime;			//空闲时间大于该数则关闭该连接
	private int isLock;				//是否锁定

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppServerName() {
		return appServerName;
	}

	public void setAppServerName(String appServerName) {
		this.appServerName = appServerName;
	}

	public String getAppViewName() {
		return appViewName;
	}

	public void setAppViewName(String appViewName) {
		this.appViewName = appViewName;
	}

	public String getAppServerIp() {
		return appServerIp;
	}

	public void setAppServerIp(String appServerIp) {
		this.appServerIp = appServerIp;
	}

	public String getAppServerPort() {
		return appServerPort;
	}

	public void setAppServerPort(String appServerPort) {
		this.appServerPort = appServerPort;
	}

	public String getAppServerAppName() {
		return appServerAppName;
	}

	public void setAppServerAppName(String appServerAppName) {
		this.appServerAppName = appServerAppName;
	}

	public String getAppServerUrl() {
		return appServerUrl;
	}

	public void setAppServerUrl(String appServerUrl) {
		this.appServerUrl = appServerUrl;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}

	public String getDBIp() {
		return DBIp;
	}

	public void setDBIp(String dBIp) {
		DBIp = dBIp;
	}

	public String getDBPort() {
		return DBPort;
	}

	public void setDBPort(String dBPort) {
		DBPort = dBPort;
	}

	public String getDBType() {
		return DBType;
	}

	public void setDBType(String dBType) {
		DBType = dBType;
	}

	public String getDBUser() {
		return DBUser;
	}

	public void setDBUser(String dBUser) {
		DBUser = dBUser;
	}

	public String getDBPwd() {
		return DBPwd;
	}

	public void setDBPwd(String dBPwd) {
		DBPwd = dBPwd;
	}

	public String getDBDriver() {
		return DBDriver;
	}

	public void setDBDriver(String dBDriver) {
		DBDriver = dBDriver;
	}

	public int getDBMaxSt() {
		return DBMaxSt;
	}

	public void setDBMaxSt(int dBMaxSt) {
		DBMaxSt = dBMaxSt;
	}

	public int getDBMaxPool() {
		return DBMaxPool;
	}

	public void setDBMaxPool(int dBMaxPool) {
		DBMaxPool = dBMaxPool;
	}

	public int getDBminPool() {
		return DBminPool;
	}

	public void setDBminPool(int dBminPool) {
		DBminPool = dBminPool;
	}

	public String getDBAutomatic() {
		return DBAutomatic;
	}

	public void setDBAutomatic(String dBAutomatic) {
		DBAutomatic = dBAutomatic;
	}

	public int getDBCkoutTimeout() {
		return DBCkoutTimeout;
	}

	public void setDBCkoutTimeout(int dBCkoutTimeout) {
		DBCkoutTimeout = dBCkoutTimeout;
	}

	public int getDBConnTestPeriod() {
		return DBConnTestPeriod;
	}

	public void setDBConnTestPeriod(int dBConnTestPeriod) {
		DBConnTestPeriod = dBConnTestPeriod;
	}

	public int getDBInitialPool() {
		return DBInitialPool;
	}

	public void setDBInitialPool(int dBInitialPool) {
		DBInitialPool = dBInitialPool;
	}

	public int getDBMaxIdleTime() {
		return DBMaxIdleTime;
	}

	public void setDBMaxIdleTime(int dBMaxIdleTime) {
		DBMaxIdleTime = dBMaxIdleTime;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
