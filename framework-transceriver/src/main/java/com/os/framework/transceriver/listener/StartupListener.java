package com.os.framework.transceriver.listener;

import com.os.framework.transceriver.server.RTUServer;
import com.os.framework.transceriver.subscribe.SubscribeClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 启动时加载数据
 *
 * @author wangbo
 */
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("framework-transceiver start========================");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    RTUServer.getInstance().run();

                    System.out.println("rtuServer.star successful ====================");
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
                    SubscribeClient.getInstance().run();

                    System.out.println("rtuServer.star successful ====================");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("--------------------------------------------------");
        System.out.println("------------framework-transceiver init------------");
        System.out.println("--------------------------------------------------");
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            RTUServer.getInstance().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------");
        System.out.println("--------gframework-transceiver destroy--------");
        System.out.println("----------------------------------------------");
    }
}