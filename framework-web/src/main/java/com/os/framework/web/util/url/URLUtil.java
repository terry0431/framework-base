package com.os.framework.web.util.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class URLUtil {
	/**
	 * 返回连接时间  若状态码不为200 则认为连接失败
	 * @param url		连接地址
	 * @param timeout	超时时间
	 * @return -1 连接超时 -2 连接地址无效
	 * author wangbo
	 * @version 1.0
	 */
	public static Long getPing(String url,int timeout){
		Long ping = -1l;
		try {
			long btime = new Date().getTime();
			URL gotourl = new URL(url);
			HttpURLConnection http = (HttpURLConnection)gotourl.openConnection();	
			http.setConnectTimeout(timeout);
			http.connect();
			if(http.getResponseCode() == 200){ 
				long etime = new Date().getTime();
				ping = etime - btime; 
			}
		} catch (MalformedURLException e) {
			System.out.println("url:"+url + " 连接超时");
			ping = -2l;
		} catch (IOException e) {
			System.out.println("url:"+url + " 连接地址无效");
			ping = -1l;
		}
		return ping;
	}
	
	public static String readFileByUrl(String urlStr) {
        String res=null;
        try {
            URL url = new URL(urlStr);  
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();  
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type", "text/html;charset=UTF-8"); 
            connection.setRequestProperty("Content-type", "application/json;charset=UTF-8"); 
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.connect();
            //得到输入流
            InputStream inputStream = connection.getInputStream();
            res = readInputStream(inputStream);
        } catch (Exception e) {
            System.out.println("通过url"+urlStr+"地址获取文本内容失败 Exception：" + e);
        }
        return res;
    }
	/**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        //System.out.println(new String(bos.toByteArray(),"utf-8"));
        return new String(bos.toByteArray(),"utf-8");
    }  
    
    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        Map m = new HashMap();
        m.put("secret",  "1931c60dc3328f95");
        m.put("columnId", "e0cebef9-c358-46d9-a402-815dccaf1c7b" );
        m.put("title","测试新闻"  );
        m.put("date", "2018-08-01" );
        m.put("source", "预报台" );
//        m.put("content","<p>01月13日10时发布的未来24小时海洋预报：</p> " +
//"  <p>黄海北部&nbsp;&nbsp;&nbsp; 浪高&nbsp; 1.2—— 1.7 米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 渤海海峡&nbsp;&nbsp;&nbsp; 浪高&nbsp; 1.2—— 1.7 米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 渤海&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 浪高&nbsp; 1.0—— 1.5 米</p> " +
//"  <p><br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 大连港&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 附近海域浪高 1.0 米；表层水温&nbsp;&nbsp; 5.2 度<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 旅顺新港&nbsp;&nbsp;&nbsp; 附近海域浪高 1.0 米；表层水温&nbsp;&nbsp; 5.0 度<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 皮口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 附近海域浪高 0.9 米；表层水温&nbsp; -1.0&nbsp; 度<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 庄河&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 附近海域浪高 0.9 米；表层水温&nbsp; -1.0 度<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 长兴岛&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 附近海域浪高 1.0 米；表层水温&nbsp;&nbsp; -1.0 度</p> " +
//"  <p><br /> 下面是各港口附近海域明天的潮汐预报：<br /> 大连港&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第一次高潮&nbsp;&nbsp;&nbsp; 00 时&nbsp; 13 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 283厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 06 时&nbsp; 48 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -13厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第二次高潮&nbsp;&nbsp;&nbsp; 12 时&nbsp; 35 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 224厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 18 时&nbsp; 45 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -23厘米<br /> 旅顺新港&nbsp;&nbsp;&nbsp; 第一次高潮&nbsp;&nbsp;&nbsp; 01 时&nbsp; 26 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 196厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 08 时&nbsp; 09 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 28厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第二次高潮&nbsp;&nbsp;&nbsp; 13 时&nbsp; 35 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 162厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 20 时&nbsp; 08 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -6厘米<br /> 长海县&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第一次高潮&nbsp;&nbsp;&nbsp; 11 时&nbsp; 48 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 326厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 06 时&nbsp; 05 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -1厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第二次高潮&nbsp;&nbsp;&nbsp; -- 时&nbsp; -- 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ---厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 18 时&nbsp; 08 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -3厘米<br /> 庄河&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第一次高潮&nbsp;&nbsp;&nbsp; 11 时&nbsp; 36 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 514厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 05 时&nbsp; 48 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 22厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第二次高潮&nbsp;&nbsp;&nbsp; 23 时&nbsp; 56 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 586厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 17 时&nbsp; 52 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 22厘米<br /> 长兴岛&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第一次高潮&nbsp;&nbsp;&nbsp; 05 时&nbsp; 55 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 168厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; 12 时&nbsp; 42 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -13厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 第二次高潮&nbsp;&nbsp;&nbsp; 18 时&nbsp; 03 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 89厘米<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 低潮&nbsp;&nbsp;&nbsp; -- 时&nbsp; -- 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --厘米</p> ");
//        
        m.put("content","11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" );
        
        try{
            String jsonArgs = mapper.writeValueAsString(m);
            System.out.println(jsonArgs.length());
            jsonArgs = URLEncoder.encode(jsonArgs,"UTF-8");
            System.out.println(jsonArgs.length());
            jsonArgs = URLEncoder.encode(jsonArgs,"UTF-8");
            System.out.println(jsonArgs.length());
//            jsonArgs = URLDecoder.decode(jsonArgs,"GBK");
//            System.out.println(jsonArgs);
            String url = "http://localhost:8080/cms/web/addNews.do?jsonArgs=" + jsonArgs;
            System.out.println("url:" + url);
            String revalue = readFileByUrl(url);
            System.out.println(revalue);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
