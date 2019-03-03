package com.os.framework.web.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.wx.WeiXinUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * @program: framework-base
 * @description: 会员Service
 * @author: wangbo
 * @create: 2019-03-01 17:50
 **/
public class MenberService {
    /**
      * @Description:
      * @param code 微信访问页面跳转后的code
      * @return:
      * @Author:wangbo
      * @Date:2019-03-01
      * @Time:17:51
    **/
    public Map getMenberByCode(String code){
        //String url =  request.getRequestURL().toString() ;
        System.out.println("====== 获取webaccess_token" + code);
        Map map = WeiXinUtils.getWebAccess_token(code);
        System.out.println("====== 获取webaccess_token 成功" + map.get("web_access_token"));
        map = getMenber(map);
        return map;
    }
    public Map getMenber(Map map){
        //TODO 之后添加检验授权凭证  如果失败刷新access_token 再失败重新获取access_token
        //Map map = new HashMap();
        String WebAccess_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+map.get("web_access_token")+"&openid="+map.get("openid")+"&lang=zh_CN";
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
            long access_timestamp = new Date().getTime();
            String message = new String(b, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            Map jsonmap = mapper.readValue(message, Map.class );
            if(jsonmap.get("errcode") != null){
                System.out.println("errcode : " + jsonmap.get("errcode"));
            }
            if(jsonmap.get("openid") != null){
                map.put("openid", jsonmap.get("openid").toString() );
                map.put("nickname", jsonmap.get("nickname").toString() );
                map.put("sex", jsonmap.get("sex").toString() );
                map.put("province", jsonmap.get("province").toString() );
                map.put("city", jsonmap.get("city").toString() );
                map.put("country", jsonmap.get("country").toString() );
                map.put("headimgurl", jsonmap.get("headimgurl").toString() );
                //web_access_token = jsonmap.get("access_token").toString();
                //web_access_token_expires_in = jsonmap.get("expires_in").toString();
            }else{
//				log.error("获取access_token失败 "  + message );
            }
//			log.info("获取微信用户信息 : " + message);
        } catch (Exception ex) {
//			log.error("获取jsApiTicket失败" ,ex);
        }
        return map;
    }
}
