package com.os.framework.mq.transceriver.queue;

import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MsgDelayed implements Delayed {
	private final long delay; //延迟时间
	private final long expire;  //到期时间
	private final Map<String, String> msg;   //数据
	private final long now; //创建时间

	public MsgDelayed(long delay, Map<String, String> msg) {
		this.delay = delay;
		this.msg = msg;
		expire = System.currentTimeMillis() + delay;    //到期时间 = 当前时间+延迟时间
		now = System.currentTimeMillis();
	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		long d = unit.convert(this.expire  - System.currentTimeMillis(), unit);
		return d;
	}
	
    public Map getMsg() {
        return msg;
    }
}
