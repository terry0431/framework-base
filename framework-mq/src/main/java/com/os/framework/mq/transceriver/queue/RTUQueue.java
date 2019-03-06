package com.os.framework.mq.transceriver.queue;

import com.os.framework.core.config.HostInfo;
import com.os.framework.mq.server.handler.TransceriverMsgHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: framework-base
 * @description: RTU消息队列
 * @author: wangbo
 * @create: 2019-03-03 20:02
 **/
public class RTUQueue {
    private static final Logger logger = LogManager.getLogger(RTUQueue.class);
    private static Map<String, LinkedBlockingQueue> rtuqueuemap = new HashMap();

    //根据RTUID 创建一个队列
    private static void createQueue(String rtuid) {
        rtuqueuemap.put(rtuid, new LinkedBlockingQueue());
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (; ; ) {
                    //发送给订阅transceiver server
                    try {
                        TransceriverMsgHandler.sendMsg(rtuid + HostInfo.SEPARATOR + rtuqueuemap.get(rtuid).take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //在指定队列中插入消息
    public static void addMsg(String rtuid, String msg) throws Exception {

        if (rtuqueuemap.get(rtuid) == null) {
            logger.debug("[queue][rtu][addmsg]rtuid:" + rtuid + " msg:" + msg);
            createQueue(rtuid);
        }else{
            logger.warn("[queue][rtu][addmsg] rtuqueue is null rtuid:" + rtuid + " msg:" + msg);
        }
        rtuqueuemap.get(rtuid).put(msg);
    }
}
