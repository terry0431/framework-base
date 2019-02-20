package com.os.framework.web.controller;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.util.wx.WeiXinUtils;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wangbo
 * @date 2016-12-29 12:02:24
 */
@Controller
public class TestController {
	
	@RequestMapping(value="/hello",method=GET)
	public String hello(){
		WeiXinUtils wxUtils = new WeiXinUtils();
		
		wxUtils.initAccessToken();
		
		MainDao dao = new MainDao();
		
		List list = dao.queryForList("select * from sys_userinfo", null);
		 
		String ss = ((Map)list.get(0) ).get("u_name") + "";
		System.out.println("user : " + ss);
		return "hello";
	}

}