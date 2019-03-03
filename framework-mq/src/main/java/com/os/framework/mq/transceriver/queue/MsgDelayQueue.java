package com.os.framework.mq.transceriver.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;

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
	 * 插入队列
	 * @param delayed
	 */
	public void offer(String rtuid,MsgDelayed delayed) {
		jmrtuqueue.get(rtuid).offer(delayed);
	}
	
	/**
	 * 等待下一个延时时间
	 * @param rtuid
	 * @return
	 * @throws InterruptedException
	 */
	public MsgDelayed waitDelayTime(String rtuid) throws InterruptedException {
		
		return jmrtuqueue.get(rtuid).peek();
	}
	
	/**
	 * 清空指定队列
	 * @param rtuid
	 */
	public void clearDelay(String rtuid) {
		jmrtuqueue.get(rtuid).clear();
	}
	
	/**
	 * 	消息分发
	 */
//	public void msgDistribution() {
//		EContorlAdapterInterface contorlPoolModelJMRtuAdapter = new EContorlPoolModelJMRtuAdapter();
//		for(String key : jmrtuqueue.keySet()) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Map<String,String> m = null;
//					for(;;) {
//						try {
//							if(jmrtuqueue.get(key) != null ) {
//								m = jmrtuqueue.get(key).take().getMsg();
//								contorlPoolModelJMRtuAdapter.sendMsg(key, m.get(key));
//								Thread.sleep(300);
//							}
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
//		}
//	}
}
