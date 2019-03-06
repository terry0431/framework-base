package com.os.framework.mq.intef;

import com.os.framework.mq.server.TransceriverConnectionServer;
import com.os.framework.mq.server.WebConnectionServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-06 09:44
 **/
@Controller
public class ServerIntef {
    @RequestMapping(value = {"ifs/mp/shutdown"},method = RequestMethod.POST)
    @ResponseBody
    public void shutdown(){
        TransceriverConnectionServer.getInstance().shutdown();
        WebConnectionServer.getInstance().shutdown();
    }

    @RequestMapping(value = {"ifs/mp/start"},method = RequestMethod.POST)
    @ResponseBody
    public void start(){
        TransceriverConnectionServer.getInstance().run();
        WebConnectionServer.getInstance().run();
    }
}