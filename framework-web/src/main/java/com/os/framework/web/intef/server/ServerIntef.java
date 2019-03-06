package com.os.framework.web.intef.server;

import com.os.framework.db.factory.DbPoolConnection;
import com.os.framework.quartz.ScheduleUtil;
import com.os.framework.web.socket.EContorlServer;
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
    @RequestMapping(value = {"ifs/web/shutdownAll"},method = RequestMethod.POST)
    @ResponseBody
    public void shutdownAll(){
        try {
            ScheduleUtil scheduleUtil = new ScheduleUtil();
            scheduleUtil.shutdown();
            EContorlServer eserver = new EContorlServer();
            eserver.shutdown();
            DbPoolConnection.getInstance().shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}