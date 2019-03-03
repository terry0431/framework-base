package com.os.framework.core.util.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @Author WangBo
 * @Date 2017-10-09
 * 微信工具类
 */
public class WeiXinUtils  {

//	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WeiXinUtils.class);
	
	private static String access_token = "e00001";  //没有正确获取微信token的值
	private static String access_token_expires_in = "-1";
	private static Long access_timestamp = 0l;
	

	
	
//	private static String  appid = "wxcdec5571cf8a3bea";
	private static String  appid = "wx987f675c28a3f494";

//	private static String appSrecret = "3c75ea857ac1709da47ba6fe3ad080af";
	private static String appSrecret = "b8170161aa7e75e866c12ff634753125";
	private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSrecret;
	
	
	private static String jsApiTicket = "e00001"; //没有正确获取微信token的值
	private static String jsApiTicket_expires_in = "-1";
	private static Long jsApi_timestamp = 0l;
	
	private static String jstkUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
	
	/**
	 * 初始化access token
	 */
	public static void initAccessToken(){
		try {
			URL getUrl=new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			access_timestamp = new Date().getTime();
			
			String message = new String(b, "UTF-8");
			ObjectMapper   mapper = new ObjectMapper();
			Map jsonmap = mapper.readValue(message, Map.class );
			if(jsonmap.get("errcode") != null){
				System.out.println("errcode : " + jsonmap.get("errcode"));
			}
			if(jsonmap.get("access_token") != null){
				access_token = jsonmap.get("access_token").toString();
				access_token_expires_in = jsonmap.get("expires_in").toString();
			}else{
//				log.error("获取access_token失败 "  + message );
			}
			
//			log.info("获取access_token : " + message);
		} catch (Exception ex) {
//			log.error("获取access_token失败 ", ex );
		}

	}
	
	
	public static String getAccessToken(){
		return access_token;
	}
	public static String getAccess_token_expires_in(){
		return access_token_expires_in;
	}
	public static Long getAccess_timestamp(){
		return access_timestamp;
	}
	
	/**
	 * 获取jsapi token
	 */
	public static void initJsApiTicket() {
		try {
			URL getUrl = new URL(jstkUrl);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			
			jsApi_timestamp = new Date().getTime();
			
			String message = new String(b, "UTF-8");
			ObjectMapper   mapper = new ObjectMapper();
			Map jsonmap = mapper.readValue(message, Map.class );
			
			if(jsonmap.get("ticket") != null){
				jsApiTicket = jsonmap.get("ticket").toString();
				jsApiTicket_expires_in = jsonmap.get("expires_in").toString();
			}
//			log.info("获取jsApiTicket : " + message);
			//System.out.println("initJsApiTicket ||" + message);
		} catch (Exception ex) {
//			log.error("获取jsApiTicket失败" ,ex);
		}

	}
	
	public static String getJsApiTicket(){
		if(jsApiTicket.equals("e00001")){
			initJsApiTicket();
		}
		return jsApiTicket;
	}
	public static String getJsApiTicket_expires_in(){
		return jsApiTicket_expires_in;
	}
	public static Long getJsApi_timestamp(){
		return jsApi_timestamp;
	}
	
	
	
	public static Map<String, String> sign(String url) {
		Map<String, String> ret = new HashMap();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsApiTicket
			+ "&noncestr=" + nonce_str
			+ "&timestamp=" + timestamp
			+ "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsApiTicket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	
	public static Map getWebAccess_token(String code){
		Map map = new HashMap();
		String WebAccess_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appSrecret+"&code="+code+"&grant_type=authorization_code";
		try {
			URL getUrl=new URL(WebAccess_url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			access_timestamp = new Date().getTime();
			
			String message = new String(b, "UTF-8");
			ObjectMapper   mapper = new ObjectMapper();
			Map jsonmap = mapper.readValue(message, Map.class );
			if(jsonmap.get("errcode") != null){
				System.out.println("errcode : " + jsonmap.get("errcode"));
			}
			if(jsonmap.get("access_token") != null){
				map.put("web_access_token", jsonmap.get("access_token").toString() );
				map.put("web_access_token_expires_in", jsonmap.get("expires_in").toString() );
				map.put("web_refresh_token", jsonmap.get("refresh_token").toString() );
				map.put("openid", jsonmap.get("openid").toString() );
				//web_access_token = jsonmap.get("access_token").toString();
				//web_access_token_expires_in = jsonmap.get("expires_in").toString();
				
			}else{
//				log.error("获取access_token失败 "  + message );
			}
			
//			log.info("获取access_token : " + message);
		} catch (Exception ex) {
//			log.error("获取jsApiTicket失败" ,ex);
		}
		return map;
	}
	

}
