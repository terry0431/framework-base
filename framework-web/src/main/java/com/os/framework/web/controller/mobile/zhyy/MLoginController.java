package com.os.framework.web.controller.mobile.zhyy;

import com.os.framework.db.dao.Dao;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.web.service.web.MenberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: framework-base
 * @description: 会员登录
 * @author: wangbo
 * @create: 2019-02-26 09:18
 **/
@Controller
public class MLoginController {
    private static final Logger logger = LogManager.getLogger(MLoginController.class);
    @RequestMapping(value = "/con/mobile/zhyy/login", method = GET)
    public String login(HttpServletRequest request){

        if(request.getSession().getAttribute("menber") != null){
            return "mobile/zhyy/index";
        }
        MenberService menberService = new MenberService();
        MainDao dao = new MainDao();
        String code = request.getParameter("code");
        logger.debug("[code]:" + code);

        Map wxinfo = menberService.getMenberByCode(code);
        logger.debug("openid:" + wxinfo.get("openid"));
        String sql = "select * from web_menber where z_weixinid = ?";
        Map menber = dao.queryForMap(sql,new Object[]{wxinfo.get("openid")});

        if(menber == null ){
            Map zhyymap = new HashMap();
            zhyymap.put("z_weixinid", wxinfo.get("openid"));
            zhyymap.put("z_touxiang", wxinfo.get("headimgurl"));
            zhyymap.put("z_sheng", wxinfo.get("province"));
            zhyymap.put("z_shi", wxinfo.get("city"));
            zhyymap.put("z_bieming", wxinfo.get("nickname"));
            zhyymap.put("z_state", -1);// 新用户 状态为 未申请
            zhyymap.put("id",dao.save("web_menber", zhyymap, PKBean.NATIVE) );
            //request.getSession().setAttribute("menber",menber);
            request.setAttribute("menber",zhyymap);
            return "mobile/zhyy/login";
        }else if( (Integer)menber.get("z_state") != 1){
            request.setAttribute("menber",menber);
            return "mobile/zhyy/login";
        }else{
            request.getSession().setAttribute("menber",menber);
            return "mobile/zhyy/index";
        }
    }
}