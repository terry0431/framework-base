package com.os.framework.web.queue.bundle.zhyy.econtorl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.os.framework.web.handler.bundle.econtorl.EContorlAdapterInterface;
import com.os.framework.web.handler.bundle.econtorl.EContorlPoolModelJMRtuAdapter;

public class MsgQueue {
	
	public static Map<String, ConcurrentLinkedQueue<Map<String,String>>> jmrtuqueue = new HashMap();
	private static MsgQueue msgQueue= null;
	/**
	 * 初始化时把所有设备的队列都实例化
	 */
	public static synchronized MsgQueue getInstance(){
		if(msgQueue == null) {
			msgQueue = new MsgQueue();
		}
		return msgQueue;
	}
	/**
	 * 服务器启动时要把所有设备加载进来
	 */
	private MsgQueue(){
		jmrtuqueue.put("hongyuan",new ConcurrentLinkedQueue());
	}
	
	private ThreadLocal<Integer> count = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return new Integer(0);
		};
	};
	
	public void addMsg(String rtuid,Map msgmap) {
		
		Integer value = count.get();
		value++;
		count.set(value);
		jmrtuqueue.get(rtuid).offer(msgmap);
//		System.out.println("create msg " + value + " size : " + map.get(rtuid).size());
	}
	
	public void getMsg(String rtuid) {
		Map msg = jmrtuqueue.get(rtuid).poll();
//		System.out.println("send 2 server "  + msg  + " size : " + queue.size());
	}
	
	/**
	 * 消息分发
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
						if(jmrtuqueue.get(key) != null && !jmrtuqueue.get(key).isEmpty()) {
							m = jmrtuqueue.get(key).poll();
							contorlPoolModelJMRtuAdapter.sendMsg(key, m.get(key));
						}
					}
				}
			}).start();
		}
	}
}
