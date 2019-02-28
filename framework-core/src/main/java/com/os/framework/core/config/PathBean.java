package com.os.framework.core.config;

import java.io.File;

public class PathBean {
	public static String BASEPATH = "";
	public static String CONTEXT = "";
	public static String WEBINF = "WEB-INF";
	public static String DBINFOPATH ;
	public static String APPINFOPATH ;
	public static String EXPORTDBINFO;
	public static String MODULEPATH;
	public static String ETLPATH;
	public static String TONGZHIPATH;
	public static String TEMPPATH;
	public static String SHANGBAOPATH;
	public static String TEMPLATEPATH;
	public static String APACHRPATH = "D:\\server\\Apache2.2\\htdocs\\";
	//配置文件夹
	private final static String CONFIGDIR = "config";
	//历史备份文件夹
	private final static String HISTORY = "_history";
	//临时文件夹
	private final static String TEMP = "_temp";

	private final static String UPLOAD = "upload";

	private final static String TEMPLATE = "template";

	public final static String LIST = "list";

	//前端附件路径
	public final static String PUBLISH_DIR = "files";

	//前端标志图路径
	public final static String PUBLISH_PIC = "pic";
	//前端附件组径路
	public final static String PUBLISH_FILES = "files";
	//前端正文附件路径
	public final static String PUBLISH_CFILES = "contentfiles";

	//后端临时 附件路径
	public final static String SITETEMP_DIR = "files";

	//后端临时  标志图路径
	public final static String SITETEMP_PIC = "pic";
	//后端临时  附件组径路
	public final static String SITETEMP_FILES = "files";

	//后端临时  正文附件路径根目录
	public final static String SITETEMP_CDIR = "web";

	//后端临时  正文附件路径
	public final static String SITETEMP_CFILES = "_contentfiles";

	public static void webInit(){
		//数据库配置文件
		//DBINFOPATH = BASEPATH + WEBINF + File.separator + "classes" + File.separator + CONFIGDIR + File.separator + "c3p0.properties";
		DBINFOPATH = BASEPATH + WEBINF + File.separator + "classes" + File.separator + CONFIGDIR + File.separator + "druid.properties";
		//全局配置文件
		APPINFOPATH = BASEPATH +  WEBINF + File.separator +   "classes" + File.separator + CONFIGDIR + File.separator + "config.properties";
		//导出数据结构
		EXPORTDBINFO = BASEPATH  + "dbinfo.html";
		//模块描述文件
		MODULEPATH = BASEPATH + WEBINF + File.separator +  "classes" + File.separator +  CONFIGDIR + File.separator + "module.properties";

		TEMPPATH = BASEPATH + WEBINF + File.separator + TEMP;
		//模板路径
		TEMPLATEPATH = BASEPATH + WEBINF + File.separator + TEMPLATE;
	}
        
	public static void testInit(String path){
		BASEPATH = path;  
		//数据库配置文件
//		DBINFOPATH =    BASEPATH  + File.separator + CONFIGDIR + File.separator + "c3p0.properties";
		DBINFOPATH =    BASEPATH  + File.separator + CONFIGDIR + File.separator + "druid.properties";
		//全局配置文件
		APPINFOPATH =   BASEPATH  + File.separator +  CONFIGDIR + File.separator + "config.properties";
		//导出数据结构
		EXPORTDBINFO =  BASEPATH + File.separator + "dbinfo.html";
		//模块描述文件
		MODULEPATH =    BASEPATH + File.separator +  CONFIGDIR + File.separator + "module.properties";

		TEMPPATH =      BASEPATH + File.separator + TEMP;
		//模板路径
		TEMPLATEPATH =  BASEPATH + File.separator + TEMPLATE;
	}
}
