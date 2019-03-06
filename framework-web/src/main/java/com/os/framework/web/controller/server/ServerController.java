package com.os.framework.web.controller.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: framework-base
 * @description: 服务管理
 * @author: wangbo
 * @create: 2019-03-06 14:05
 **/
@Controller
public class ServerController {
    @RequestMapping(value = "/con/server/servermanager" ,method = GET)
    public String serverManager(){
        return "server/servermanager";
    }
}
