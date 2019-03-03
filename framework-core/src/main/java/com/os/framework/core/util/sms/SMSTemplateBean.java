package com.os.framework.core.util.sms;

//import com.os.framework.db.dao.MainDao;

public class SMSTemplateBean {
	
//	public static String getContent(String mubanid,Map<String, Map<String,Object>> data)throws Exception{
//		MainDao dao = new MainDao();
//		Map map = dao.queryForMap("select * from oa_smsmuban where id=?", new Object[]{mubanid});
//		String content = "";
//		String tmpStr = "";
//		String flagStr = "";
//		String recontent = "";
//		if(map != null && map.get("s_content") != null){
//			content = map.get("s_content").toString();
//			while(content.indexOf("#_") > -1){
//				flagStr = content.substring(content.indexOf("#_") + 2,content.indexOf("_#"));
//				//替换标签 标签格式  $_tablename:clumnname_$
//				String tname = flagStr.substring(0,flagStr.indexOf(":") );
//				String cname = flagStr.substring(flagStr.indexOf(":") + 1,  flagStr.length() ) ;
//				String reflag = "#_" + flagStr + "_#";
//				String redata = data.get( tname ).get( cname ).toString();
//				System.out.println(content.indexOf(reflag) +  content.replaceAll(reflag, "\\" + redata ) );
//				content = content.replaceAll(reflag, redata );
//			}
//		}
//		return content;
//	}
	
//	public static void main(String[] args){
//		String content = "按时大声道 #_oa_tongzhi:t_biaoti_#";
//		String reflag = "#_oa_tongzhi:t_biaoti_#";
//		System.out.println(content.replaceAll(reflag, "大家好") );
//	}
}
