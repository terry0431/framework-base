package com.os.framework.web.controller.mobile.zhyy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: framework-base
 * @description: 会员登录
 * @author: wangbo
 * @create: 2019-02-26 09:18
 **/
@Controller
public class MLoginController {
    @RequestMapping(value = "/con/mobile/zhyy/login", method = GET)
    public String login(HttpServletRequest request){
        return "mobile/zhyy/login";
    }
}