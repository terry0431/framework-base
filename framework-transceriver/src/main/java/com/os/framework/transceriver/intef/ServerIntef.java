package com.os.framework.transceriver.intef;

import com.os.framework.transceriver.server.RTUServer;
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
    @RequestMapping(value = {"ifs/tc/shutdown"},method = RequestMethod.POST)
    @ResponseBody
    public void shutdown(){
        RTUServer.getInstance().shutdown();
    }
    @RequestMapping(value = {"ifs/tc/start"},method = RequestMethod.POST)
    @ResponseBody
    public void start(){
        RTUServer.getInstance().run();
    }
}