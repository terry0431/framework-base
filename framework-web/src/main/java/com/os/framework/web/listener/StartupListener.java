package com.os.framework.web.listener;

import com.os.framework.core.config.PathBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.mapping.AppMapping;
import com.os.framework.db.mapping.TableInfoMapping;
import com.os.framework.quartz.ScheduleUtil;
import com.os.framework.quartz.jobs.bundle.zhyy.ShuiCengKongzhi;
import com.os.framework.quartz.jobs.bundle.zhyy.YangshuijiKongzhi;
import com.os.framework.web.bean.CommonBean;
import com.os.framework.web.cache.bundle.zhyy.SystemCache;
import com.os.framework.web.queue.bundle.zhyy.econtorl.MsgDelayQueue;
import com.os.framework.web.queue.bundle.zhyy.econtorl.MsgQueue;
import com.os.framework.web.service.bundle.zhyy.CheckRTUData;
import com.os.framework.web.service.bundle.zhyy.Message;
import com.os.framework.web.socket.EContorlServer;
import com.os.framework.web.socket.NIOServer;
import com.os.framework.web.socket.NIOServerQixiang;
import com.os.framework.web.util.wx.WeiXinUtils;
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

        AppMapping.initAppMapping();
        System.out.println("appMapping init successful ====================");
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
        
        CheckRTUData checkRTUData = new CheckRTUData();
        
        System.out.println("CheckRTUData init successful ====================");
        
        Message message = new Message();
        message.initMsg();
        System.out.println("MsgBean init successful ====================");
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    NIOServer nserver = new NIOServer();
                    nserver.start();
                    System.out.println("nserver.star successful ====================");
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
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                try {
//                    NIOServerQixiang nserverqx = new NIOServerQixiang();
//                    nserverqx.start();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        System.out.println("socket init successful ========================");

//		System.out.println("rmi init successful ========================");
        

        //KTBean ktbean = new KTBean();
        //ktbean.loadKT();
        //System.out.println("exam kaoti init successful ========================");
        ShuiCengKongzhi sckongzhi = new ShuiCengKongzhi();
        sckongzhi.initSCSet();
        System.out.println("shuicengkongzhi init successful ========================");
        
        YangshuijiKongzhi ysjkongzhi = new YangshuijiKongzhi();
        ysjkongzhi.initYSJSet();
        System.out.println("yangshuijikongzhi init successful ========================");
        
        
//        WeiXinUtils wxUtils = new WeiXinUtils();
//	wxUtils.initAccessToken();
//	wxUtils.initJsApiTicket();
//	System.out.println("wx access token  init successful ========================");
                
        ScheduleUtil scheduleUtile = new ScheduleUtil();
        scheduleUtile.init();
        System.out.println("schedule init successful ========================");
        
        MsgDelayQueue.getInstance().msgDistribution();
        
        System.out.println("MsgDelayQueue Message Distribution Successful ========================");
        
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

            NIOServer nserver = new NIOServer();
            nserver.shutdown();

            EContorlServer eserver = new EContorlServer();
            eserver.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------------------");
        System.out.println("--------gframework destroy--------");
        System.out.println("-------------------------------------");
    }
}
