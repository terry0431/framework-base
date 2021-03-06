package com.os.framework.web.listener;

import com.os.framework.core.config.PathBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.mapping.TableInfoMapping;
import com.os.framework.quartz.ScheduleUtil;
import com.os.framework.quartz.jobs.bundle.zhyy.ShuiCengKongzhi;
import com.os.framework.quartz.jobs.bundle.zhyy.YangshuijiKongzhi;
import com.os.framework.core.config.CommonBean;
import com.os.framework.web.cache.zhyy.SystemCache;
import com.os.framework.web.service.zhyy.Message;
import com.os.framework.web.socket.EContorlServer;
import com.os.framework.web.subscribe.SubscribeClient;
import org.apache.logging.log4j.LogManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 启动时加载数据
 *
 * @author wangbo
 */
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("framework service start========================");
        String rootdir = servletContextEvent.getServletContext().getRealPath("");
        String context = rootdir.substring(rootdir.lastIndexOf("\\") + 1, rootdir.length());
        if (context.toUpperCase().equals("ROOT")) {
            context = "/";
        } else {
            context = "/" + context;
        }
        PathBean.BASEPATH = rootdir;
        PathBean.CONTEXT = context;
        //PathBean.testInit(System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes");
        PathBean.webInit();
        System.out.println("pathbean init successful=======================");

//        AppMapping.initAppMapping();
//        System.out.println("appMapping init successful ====================");
        TableInfoMapping.initTableInfoMapping();
        System.out.println("MainMapping init successful ===================");

        MainDao mainDao = new MainDao();
        List<Map<String, Object>> list = mainDao.queryForList("SELECT * FROM sys_param", null);
        Map<String, String> map = new HashMap();
        for (Map<String, Object> i : list) {
                map.put(i.get("p_name").toString(), i.get("p_value").toString());
        }
        CommonBean.globalParamMap = map;
        CommonBean.servletContext = servletContextEvent.getServletContext();
        System.out.println("CommonBean init successful ====================");
        
        SystemCache syscache = new SystemCache();
        syscache.init_yzcUser();
        syscache.init_baojingsz();
        System.out.println("SystemCache init successful ====================");
        
//        CheckRTUData checkRTUData = new CheckRTUData();
        
        System.out.println("CheckRTUData init successful ====================");
        
        Message message = new Message();
        message.initMsg();
        System.out.println("MsgBean init successful ====================");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    SubscribeClient.getInstance().run();
                    System.out.println("SubscribeClient successful ====================");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

        
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    EContorlServer eserver = new EContorlServer();
                    eserver.start();
                    System.out.println("eserver.star successful ====================");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("socket init successful ========================");


        ShuiCengKongzhi sckongzhi = new ShuiCengKongzhi();
        sckongzhi.initSCSet();
        System.out.println("shuicengkongzhi init successful ========================");

        YangshuijiKongzhi ysjkongzhi = new YangshuijiKongzhi();
        ysjkongzhi.initYSJSet();
        System.out.println("yangshuijikongzhi init successful ========================");
//

        ScheduleUtil scheduleUtile = new ScheduleUtil();
        scheduleUtile.init();
        System.out.println("schedule init successful ========================");


        System.out.println("--------------------------------------");
        System.out.println("------------framework init------------");
        System.out.println("--------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {

            Scheduler scheduler = sf.getScheduler();
            scheduler.shutdown();

            EContorlServer eserver = new EContorlServer();
            eserver.shutdown();
            LogManager.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------------------");
        System.out.println("--------gframework destroy--------");
        System.out.println("-------------------------------------");
    }
}
