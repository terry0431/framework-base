package com.os.framework.web.bean.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



public class SMSBean {
	
	private static String x_id="短信发送1";
	private static String x_pwd="123456aa";
	
	public static String SendSms(String mobile,String content) throws UnsupportedEncodingException{
		Integer x_ac=10;//发送信息
		HttpURLConnection httpconn = null;
		String result="";
		StringBuilder sb = new StringBuilder();
		//sb.append("http://service.winic.org:8009/sys_port/gateway/index.asp?");
		sb.append("http://service2.winic.org/Service.asmx/SendMessages?");
		//以下是参数
		//为了你的测试方便收到短信！请短信内容编辑为：你的验证码为：123456【中正云通信】
		sb.append("uid=").append(URLEncoder.encode(x_id, "utf-8"));
		sb.append("&pwd=").append(x_pwd);
		sb.append("&tos=").append(mobile);
		sb.append("&msg=").append(URLEncoder.encode(content, "utf-8")); 
		sb.append("&otime=").append("");
		try {
			URL url = new URL(sb.toString());
			httpconn = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
			while(rd.ready() ){
				result += rd.readLine();
			}
			rd.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(httpconn!=null){
				httpconn.disconnect();
				httpconn=null;
			}

		}
		return result;
	}
	static HttpURLConnection httpConn ;
	public static String post(String path,String params) throws Exception{ 
		BufferedReader in=null;
		PrintWriter out=null;   
		try {                 
			URL url=new URL(path);
			httpConn=(HttpURLConnection)url.openConnection(); 
			httpConn.setRequestMethod("POST"); 
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true); 

			out=new PrintWriter(httpConn.getOutputStream());
			out.println(params);
			out.flush();

			if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){  
				StringBuffer content=new StringBuffer(); 
				String tempStr="";  
				in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));   
				while((tempStr=in.readLine())!=null){   
					content.append(tempStr);   
				}   
				return content.toString();
			}else{   
				throw new Exception("请求出现了问题!");  
			}  
		} catch (IOException e) {  
			e.printStackTrace();   
		}finally{   
			in.close();   
			out.close();   
			httpConn.disconnect();  
		}    
			return null;   
	}   
	public static void main(String[] args){
		String mobile = "17743499664";
		String content = "您有新的通知请查收《关于2018年工作的具体内容》 2018-01-29";
		try {
			String[] ss = new String[]{"17743499664"};
			for(String s : ss){
				String revalue = SendSms(s,content);
				System.out.println(revalue);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
