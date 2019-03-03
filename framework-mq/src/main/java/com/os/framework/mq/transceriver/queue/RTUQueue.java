package com.os.framework.mq.transceriver.queue;

import com.os.framework.mq.server.handler.WebMsgHandler;

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
    Map<String, LinkedBlockingQueue> rtuqueuemap = new HashMap();
    //根据RTUID 创建一个队列
    public void createQueue(String rtuid){
        if(rtuqueuemap.get(rtuid) == null) {
            rtuqueuemap.put(rtuid,new LinkedBlockingQueue() );

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    for(;;) {
                        if(rtuqueuemap.get(rtuid) != null && !rtuqueuemap.get(rtuid).isEmpty()) {
                            //发送给订阅web server
                            try {
                                WebMsgHandler.sendMsg(rtuqueuemap.get(rtuid).take());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }

    }
    //获取指定队列
    public LinkedBlockingQueue getQueue(String rtuid){
        return rtuqueuemap.get(rtuid);
    }

    //在指定队列中插入消息
    public void addMsg(String rtuid,String msg)throws Exception{
        rtuqueuemap.get(rtuid).put(msg);
    }


    public void msgDistribution() {
//		EContorlAdapterInterface contorlPoolModelJMRtuAdapter = new EContorlPoolModelJMRtuAdapter();
		for(String key : rtuqueuemap.keySet()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Map<String,String> m = null;
					for(;;) {
						if(rtuqueuemap.get(key) != null && !rtuqueuemap.get(key).isEmpty()) {
							//发送给订阅web server
						}
					}
				}
			}).start();
		}
	}
}
