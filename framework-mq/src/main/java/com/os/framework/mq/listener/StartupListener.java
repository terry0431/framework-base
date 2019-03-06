package com.os.framework.mq.listener;

import com.os.framework.mq.server.TransceriverConnectionServer;
import com.os.framework.mq.server.WebConnectionServer;
import com.os.framework.mq.transceriver.queue.WebQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动时加载数据
 *
 * @author wangbo
 */
public class StartupListener implements ServletContextListener {

    Thread t1 ;
    Thread t2;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("framework-transceiver start========================");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    System.out.println("tcServer.star successful ====================");
                    TransceriverConnectionServer.getInstance().run();
                    System.out.println("tcServer.shutdown successful ====================");

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
                    System.out.println("web.star successful ====================");
                    WebConnectionServer.getInstance().run();
                    System.out.println("web.shutdown successful ====================");


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
        WebQueue.msgDistribution();
        System.out.println("-----------------------------------------");
        System.out.println("------------framework-mq init------------");
        System.out.println("-----------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            TransceriverConnectionServer.getInstance().shutdown();
            WebConnectionServer.getInstance().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------");
        System.out.println("--------gframework-mq destroy--------");
        System.out.println("-------------------------------------");
    }
}