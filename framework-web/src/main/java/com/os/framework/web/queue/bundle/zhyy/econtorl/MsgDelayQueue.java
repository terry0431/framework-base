package com.os.framework.web.queue.bundle.zhyy.econtorl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.os.framework.web.handler.bundle.econtorl.EContorlAdapterInterface;
import com.os.framework.web.handler.bundle.econtorl.EContorlPoolModelJMRtuAdapter;

public class MsgDelayQueue {
	
	
	private static MsgDelayQueue delayQueue;
	public static Map<String, DelayQueue<MsgDelayed>> jmrtuqueue = new HashMap();
	
	public static synchronized MsgDelayQueue getInstance() {
		if(delayQueue == null) {
			delayQueue = new MsgDelayQueue();
		}
		return delayQueue;
	}
	private MsgDelayQueue(){
		jmrtuqueue.put("hongyuan",new DelayQueue<MsgDelayed>());
	}
	
	/**
	 *	 插入队列
	 * @param delayed
	 */
	public void offer(String rtuid,MsgDelayed delayed) {
		jmrtuqueue.get(rtuid).offer(delayed);
	}
	
	/**
	 * 	消息分发
	 */
	public void msgDistribution() {
		EContorlAdapterInterface contorlPoolModelJMRtuAdapter = new EContorlPoolModelJMRtuAdapter();
		for(String key : jmrtuqueue.keySet()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Map<String,String> m = null;
					for(;;) {
						try {
							if(jmrtuqueue.get(key) != null ) {
								m = jmrtuqueue.get(key).take().getMsg();
								contorlPoolModelJMRtuAdapter.sendMsg(key, m.get(key));
								Thread.sleep(300);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
}
