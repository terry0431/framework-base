package com.os.framework.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.os.framework.core.exception.BSException;
import com.os.framework.core.secret.DesBean;

public class PropertiesUtil {
	
	public static Properties getProp(String path) throws Exception{
		InputStream in = new BufferedInputStream(new FileInputStream( path));
		Properties p;
		try {
			p = new Properties();
			p.load(in);
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			username = DesBean.decrypt(username);
			password = DesBean.decrypt(password);
			p.setProperty("username", username);
			p.setProperty("password", password);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return p;
	}
}
