package core.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author wangbo
 */
public class Config {

	//    ResourceBundle rb = null;
	private static Config config = null;
	private static Map<String, Properties> propmap = null;
	private Properties properties;

	private String dbtype;
	private String dbname;
	private String def_date_format;
	private String def_datetime_format;
	private String def_time_format;

	private String def_dbdatetime_format;
	private String def_dbdate_format;
	private String def_dbtime_format;

	private String ip;
	private String rmiport;
	private String rminame;

	private String maxsize;
	
	public static Map<String, String> globalParamMap;

	/**
	 * 获得配置表属性值
	 * @param param_name 属性名称
	 * @return 
	 */
	public static String getParamValue(String param_name) {
		return globalParamMap.get(param_name);
	}

	public String getMaxsize() {
		return maxsize;
	}

	private void setMaxsize(String maxsize) {
		this.maxsize = maxsize;
	}

	public String getRminame() {
		return rminame;
	}

	private void setRminame(String rminame) {
		this.rminame = rminame;
	}


	public String getDbname() {
		return dbname;
	}


	private void setDbname(String dbname) {
		this.dbname = dbname;
	}
	private boolean debug;

	private Config() {
		InputStream in ;
		Properties p ;
		try {
			propmap = new HashMap();
			in = new BufferedInputStream(new FileInputStream(PathBean.DBINFOPATH));
			p = new Properties();
			p.load(in);
			propmap.put(ConfigBean.DBCONFIG, p);

			//propmap = new HashMap();
			in = new BufferedInputStream(new FileInputStream(PathBean.APPINFOPATH));
			p = new Properties();
			p.load(in);
			propmap.put(ConfigBean.APPCONFIG, p);
			setDbtype(p.getProperty("dbtype"));
			setDbname(p.getProperty("dbname"));

			if (p.getProperty("debug").equals("true")) {
				setDebug(true);
			} else {
				setDebug(false);
			}

			setDef_date_format(p.getProperty("def_date_format"));
			setDef_datetime_format(p.getProperty("def_datetime_format"));
			setDef_time_format(p.getProperty("def_time_format"));

			setDef_dbdate_format(p.getProperty("def_dbdate_format"));
			setDef_dbdatetime_format(p.getProperty("def_dbdatetime_format"));
			setDef_dbtime_format(p.getProperty("def_dbtime_format"));

			setIp(p.getProperty("ip"));
			setRmiport(p.getProperty("rmiport"));
			setRminame(p.getProperty("rminame"));
			setMaxsize(p.getProperty("maxsize"));

			//propmap = new HashMap();
			//in = new BufferedInputStream(new FileInputStream(PathBean.MODULEPATH));
			//p = new Properties();
			//p.load(in);
			//propmap.put(ConfigBean.MODCONFIG, p);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	private void setIp(String ip) {
		this.ip = ip;
	}

	public String getRmiport() {
		return rmiport;
	}

	private void setRmiport(String rmiport) {
		this.rmiport = rmiport;
	}

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}

	/**
	 * 根据配置文件、key 返回 value
	 *
	 * @param cName	配置文件
	 * @param key
	 * @return
	 */
	public String getValue(String cName, String key) {
		properties = (Properties) propmap.get(cName);
		if (properties.getProperty(key) != null) {
			return properties.getProperty(key);
		} else {
			return key;
		}
	}

	public String getDbtype() {
		return dbtype;
	}

	private void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public String getDef_date_format() {
		return def_date_format;
	}

	private void setDef_date_format(String defDateFormat) {
		def_date_format = defDateFormat;
	}

	public String getDef_datetime_format() {
		return def_datetime_format;
	}

	private void setDef_datetime_format(String defDatetimeFormat) {
		def_datetime_format = defDatetimeFormat;
	}

	public String getDef_time_format() {
		return def_time_format;
	}

	private void setDef_time_format(String defTimeFormat) {
		def_time_format = defTimeFormat;
	}

	public String getDef_dbdatetime_format() {
		return def_dbdatetime_format;
	}

	private void setDef_dbdatetime_format(String defDbdatetimeFormat) {
		def_dbdatetime_format = defDbdatetimeFormat;
	}

	public String getDef_dbdate_format() {
		return def_dbdate_format;
	}

	private void setDef_dbdate_format(String defDbdateFormat) {
		def_dbdate_format = defDbdateFormat;
	}

	public String getDef_dbtime_format() {
		return def_dbtime_format;
	}

	private void setDef_dbtime_format(String defDbtimeFormat) {
		def_dbtime_format = defDbtimeFormat;
	}

	public boolean getDebug() {
		return debug;
	}

	private void setDebug(boolean debug) {
		this.debug = debug;
	}
}
