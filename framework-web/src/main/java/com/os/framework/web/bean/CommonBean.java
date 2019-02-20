package com.os.framework.web.bean;

import java.util.Map;

import javax.servlet.ServletContext;

public class CommonBean {

	public static Map<String, String> globalParamMap;
	public static ServletContext servletContext;

	/**
	 * 
	 * @param param_name
	 * @return 
	 */
	public static String getParamValue(String param_name) {
		return globalParamMap.get(param_name);
	}

}
