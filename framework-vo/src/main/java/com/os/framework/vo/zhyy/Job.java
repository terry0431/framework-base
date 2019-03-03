package com.os.framework.vo.zhyy;

public class Job {
	private boolean run_state = false; //当前任务执行状态 
	private Integer[] jobinfo;	//任务信息
	private Integer[] lineNum;	//设备通道号
	private Integer elcnum;//饵料池号
	private Integer concurrentNum; //并发数
	private Integer[] equipmentNum;//设备号
	
	private long runtime;
	private long currenttime;
//	private Integer swnum;	//顺位
//	private Integer[] sbtd;	//设备通道
	
	
	public Integer[] getLineNum() {
		return lineNum;
	}
	public void setLineNum(Integer[] lineNum) {
		this.lineNum = lineNum;
	}
	public Integer[] getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(Integer[] equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	public boolean isRun_state() {
		return run_state;
	}
	public void setRun_state(boolean run_state) {
		this.run_state = run_state;
	}
	public Integer[] getJobinfo() {
		return jobinfo;
	}
	public void setJobinfo(Integer[] jobinfo) {
		this.jobinfo = jobinfo;
	}
	public Integer getConcurrentNum() {
		return concurrentNum;
	}
	public void setConcurrentNum(Integer concurrentNum) {
		this.concurrentNum = concurrentNum;
	}
	public Integer getElcnum() {
		return elcnum;
	}
	public void setElcnum(Integer elcnum) {
		this.elcnum = elcnum;
	}
	public long getRuntime() {
		return runtime;
	}
	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}
	public long getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(long currenttime) {
		this.currenttime = currenttime;
	}
}
