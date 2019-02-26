package com.os.framework.web.bean.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.os.framework.web.handler.econtorl.EContorlAdapterInterface;
import com.os.framework.web.handler.econtorl.EContorlPoolModelJMRtuAdapter;
import com.os.framework.web.queue.zhyy.econtorl.MsgDelayQueue;
import com.os.framework.web.queue.zhyy.econtorl.MsgDelayed;

/**
 * 输水任务执行
 * @author wangbo
 *
 */
public class JobBean {
	private static Map<String, Job> runmap = new HashMap();
	volatile int count;	//任务总数
	volatile int linenum = 0; //任务并发数
	/**
	 * 执行任务
	 * @param rtuid	设备id
	 * @return
	 */
	public String runJob(String rtuid) {
		EContorlAdapterInterface contorlAdapter = new EContorlPoolModelJMRtuAdapter();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Job job = runmap.get(rtuid);
				job.setRuntime(System.currentTimeMillis() );
				count = job.getJobinfo().length ;
				
				try {
					//先打开饵料池开关
					Thread maint = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								contorlAdapter.switchControl(rtuid,0 ,job.getElcnum() , 1 ,0);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					},"mythread_elc");
					maint.start();
					maint.join();
					//饵料池任务执行完毕 开始执行其他任务
//					int concurrentNum = job.getConcurrentNum();
					
//					Integer[] states = new Integer[concurrentNum];
//					Integer[] lasttime = new Integer[concurrentNum];
					//int i = 0;
					CountDownLatch threadSignal = new CountDownLatch(job.getJobinfo().length);//初始化countDown  
					MsgDelayed delayed = null;
					long delayedtime = 0; 
					for(int i = 0;i < job.getJobinfo().length ;i ++) {
						System.out.println("linenum:"+ linenum);
						if(linenum < job.getConcurrentNum()) {
							linenum ++;
							new Thread(new Runnable() {
								@Override
								public void run() {
									int thiscount = job.getJobinfo().length - (count-- );
									System.out.println("任务"+ thiscount +"开始");
									try {
										
										// TODO Auto-generated method stub
										//打开开关
										contorlAdapter.switchControl(rtuid,
												job.getEquipmentNum()[thiscount],
												job.getLineNum()[thiscount], 
												1,
												0);
										int time = job.getJobinfo()[thiscount];
										
										//关闭开关
										contorlAdapter.switchControl(rtuid,
												job.getEquipmentNum()[thiscount],
												job.getLineNum()[thiscount], 
												1,
												time * 1000);
										Thread.sleep(time * 1000);
										//倒计时
//										while (time > 0) {
//											System.out.println("任务"+thiscount +"倒计时:"+time);
//											time--;
//											try {
//												Thread.sleep(1000);
//												runmap.get(rtuid).getJobinfo()[thiscount] = time;//更新任务时间
//											} catch (InterruptedException e) {
//												e.printStackTrace();
//											}
//										}
//										//关闭开关
//										contorlAdapter.switchControl(rtuid,
//												job.getEquipmentNum()[thiscount],
//												job.getLineNum()[thiscount], 
//												0);
										System.out.println("任务"+ thiscount +"结束==============");
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}finally {
										linenum --;
										threadSignal.countDown();
										System.out.println("剩余任务==========" + threadSignal.getCount() );
									}
									//最后一个任务 改用 CountDownLatch
//									if( count == 1) {
//										runmap.get(rtuid).setRun_state(false);
//									}
								}
							},"mythread_job").start(); 
						}else {
							System.out.println("等待开启下个任务==========");
							delayed = MsgDelayQueue.getInstance().waitDelayTime(rtuid);
							if(delayed == null) {
								Thread.sleep(1000);
							}else {
								delayedtime = delayed.getDelay(TimeUnit.MILLISECONDS);
								System.out.println("下个任务延时==========" + delayedtime);
								if(delayedtime > 0) {
									Thread.sleep( delayedtime );
								}else {
									Thread.sleep(1000);
								}
							}
							i --;
						}
					}
					threadSignal.await();//等待所有线程执行完毕
					System.out.println("====  子任务结束  ======");
					maint = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								contorlAdapter.switchControl(rtuid,0 ,job.getElcnum() , 0,0);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					},"mythread_elc");
					maint.start();
					maint.join();
					//runmap.get(rtuid).setRun_state(false);
					runmap.remove(rtuid);
					System.out.println("====  全部任务结束  ======");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("清空任务队列 停止执行操作==========" );
					MsgDelayQueue.getInstance().clearDelay(rtuid); //清空任务队列 停止执行操作
					if(runmap != null) {
						System.out.println("清空任务信息==========" );
						runmap.remove(rtuid); //清空任务信息
					}
					e.printStackTrace();
				}
			}
		},"mythread_runjob").start();
		return "1";
	}
	
	
	/**
	 * 设置任务
	 * @param job	任务对象
	 * @param rtuid	养殖场id
	 */
	public static void setJob(Job job,String rtuid) {
		runmap.put(rtuid,job);
	}
	/**
	 * 获取任务
	 * @param rtuid
	 * @return
	 */
	public static Job getJob(String rtuid) {
		return runmap.get(rtuid);
	}
	
}
