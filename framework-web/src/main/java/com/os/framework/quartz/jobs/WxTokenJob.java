package com.os.framework.quartz.jobs;

import com.os.framework.core.util.wx.WeiXinUtils;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author wangbo
 * @date 2017-10-9 14:10:37
 */
public class WxTokenJob  implements Job{
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		WeiXinUtils wxUtils = new WeiXinUtils();
		long jg = 1000 * 60 * 15; //间隔时间
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
		
		String  access_token =  WeiXinUtils.getAccessToken();
		Long Access_timestamp =  WeiXinUtils.getAccess_timestamp();
		Long Access_token_expires_in = Long.valueOf( WeiXinUtils.getAccess_token_expires_in() );
		
		String  jsApiTicket =  WeiXinUtils.getJsApiTicket();
		Long jsApiTicket_timestamp =  WeiXinUtils.getJsApi_timestamp();
		Long jsApiTicket_token_expires_in = Long.valueOf( WeiXinUtils.getJsApiTicket_expires_in());
		
		Long nowTime = new Date().getTime();
		//当前时间 - 时间戳 > 有效时间 - 间隔时间  注意： 间隔时间为调度任务执行间隔 +５分钟
		if(nowTime -  Access_timestamp < (Access_token_expires_in + jg )  || Access_timestamp == 0l ){
			wxUtils.initAccessToken();
		}
		if(nowTime -  jsApiTicket_timestamp < (jsApiTicket_token_expires_in + jg )  || jsApiTicket_timestamp == 0l ){
			wxUtils.initJsApiTicket();
		}
	} 
}