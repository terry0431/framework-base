package com.os.framework.web.intef.wx;

import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.web.util.wx.WeiXinUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangbo
 * @date 2017-10-13 12:02:24 微信工具类
 */
@Controller
public class WeiXinIntfc {

    /**
     * 获取access token
     *
     * @return
     */
    @RequestMapping(value = "/wx/getAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAccessToken() {
        String access_token = WeiXinUtils.getAccessToken();
        Long Access_timestamp = WeiXinUtils.getAccess_timestamp();
        Long Access_token_expires_in = Long.valueOf(WeiXinUtils.getAccess_token_expires_in());

        String jsApiTicket = WeiXinUtils.getJsApiTicket();
        Long jsApiTicket_timestamp = WeiXinUtils.getJsApi_timestamp();
        Long jsApiTicket_token_expires_in = Long.valueOf(WeiXinUtils.getJsApiTicket_expires_in());

        Map map = new HashMap();
        map.put("access_token", access_token);
        map.put("Access_timestamp", Access_timestamp);
        map.put("Access_token_expires_in", Access_token_expires_in);

        return map;
    }

    /**
     * 获取js token
     *
     * @return
     */
    @RequestMapping(value = "/wx/getJsApiTicket", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getJsApiTicket() {

        String jsApiTicket = WeiXinUtils.getJsApiTicket();
        Long jsApiTicket_timestamp = WeiXinUtils.getJsApi_timestamp();
        Long jsApiTicket_token_expires_in = Long.valueOf(WeiXinUtils.getJsApiTicket_expires_in());

        Map map = new HashMap();
        map.put("jsApiTicket", jsApiTicket);
        map.put("jsApiTicket_timestamp", jsApiTicket_timestamp);
        map.put("jsApiTicket_token_expires_in", jsApiTicket_token_expires_in);

        return map;
    }

    /**
     * 获取j微信s 接口初始化参数
     *
     * @return
     */
    @RequestMapping(value = "/wx/getConfigParam", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getConfigParam(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Map map = WeiXinUtils.sign(url);

        return map;
    }

    /**
     * 微信用户登入
     *
     * @return
     */
    @RequestMapping(value = "/wx/getMenber", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMenber(HttpServletRequest request, String code) {
        MainDao dao = new MainDao();
        //String url =  request.getRequestURL().toString() ;
        System.out.println("====== 获取webaccess_token" + code);
        Map map = WeiXinUtils.getWebAccess_token(code);
        System.out.println("====== 获取webaccess_token 成功" + map.get("web_access_token"));
        map = WeiXinUtils.getMenber(map);
        System.out.println("====== 获取getMenber 成功" + map.get("openid"));
        if(map.get("openid") == null ){
            return null;
        }
        Map zhyymap = null;
        //查看是否有注册过
        String sql = "select count(*) as cnum from zhyy_zhanghao where z_weixinid = ?";
        try {
            long count = (Long) dao.queryForMap(sql, new Object[]{map.get("openid")}).get("cnum");
            //添加会员
            
            if (count == 0) {
                zhyymap = new HashMap();
                zhyymap.put("z_weixinid", map.get("openid"));
                zhyymap.put("z_touxiang", map.get("headimgurl"));
                zhyymap.put("z_sheng", map.get("province"));
                zhyymap.put("z_shi", map.get("city"));
                zhyymap.put("z_bieming", map.get("nickname"));
                zhyymap.put("z_state", -1);// 新用户 状态为 未申请
                dao.save("zhyy_zhanghao", zhyymap, PKBean.NATIVE);
                request.getSession().setAttribute("menber", zhyymap);
            } else {
                zhyymap = dao.queryForMap("select * from zhyy_zhanghao", new Object[]{});
            }
            request.getSession().setAttribute("menber", zhyymap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zhyymap;
    }
}
