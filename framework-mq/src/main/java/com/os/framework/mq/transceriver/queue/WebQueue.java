package com.os.framework.mq.transceriver.queue;

import com.os.framework.mq.server.handler.WebMsgHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: framework-base
 * @description: web消息队列
 * @author: wangbo
 * @create: 2019-03-03 20:02
 **/
public class WebQueue {
    private static final Logger logger = LogManager.getLogger(WebQueue.class);
    private static LinkedBlockingQueue webqueue = new LinkedBlockingQueue();

    //在队列中插入消息
    public static void addMsg(String msg) throws Exception {
        logger.debug("[queue][web][addmsg]" + msg);
        webqueue.put(msg);
    }


    public static void msgDistribution() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Map<String, String> m = null;
                for (; ; ) {
                        try {
                            WebMsgHandler.sendMsg(webqueue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                    }
                }
            }
        }).start();
    }
}
