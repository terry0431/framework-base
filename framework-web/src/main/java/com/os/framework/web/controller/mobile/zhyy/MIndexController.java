package com.os.framework.web.controller.mobile.zhyy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: framework-base
 * @description: 移动端首页控制器
 * @author: wangbo
 * @create: 2019-02-26 14:11
 **/
@Controller
public class MIndexController {
    @RequestMapping(value = "/con/mobile/zhyy/index", method = GET)
    public String index(HttpServletRequest request){
        return "mobile/zhyy/index";
    }
}
