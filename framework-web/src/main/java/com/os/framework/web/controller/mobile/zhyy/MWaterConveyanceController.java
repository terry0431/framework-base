package com.os.framework.web.controller.mobile.zhyy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: framework-base
 * @description: 输水管理
 * @author: wangbo
 * @create: 2019-02-26 09:20
 **/
@Controller
public class MWaterConveyanceController {

    @RequestMapping(value = "/con/mobile/zhyy/waterConveyance/index", method = GET)
    public String index(HttpServletRequest request){
        return "mobile/zhyy/pool/index";
    }

}
