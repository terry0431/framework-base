package com.os.framework.web.controller.zhyy;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WaterConveyanceController {

    @RequestMapping("/con/econtrol/test")
    public String roleList(HttpServletRequest request) {
        return "econtrol/test/test";
    }
    @RequestMapping("/con/econtrol/kongzhi")
    public String kongzhi(HttpServletRequest request) {
        return "econtorl/kongzhi/kongzhi";
    }
}