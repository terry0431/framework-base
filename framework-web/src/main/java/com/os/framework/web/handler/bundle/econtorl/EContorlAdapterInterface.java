package com.os.framework.web.handler.bundle.econtorl;

import java.util.Map;

/**
 * 不同型号设备适配器接口
 * @author wangbo
 *
 */
public interface EContorlAdapterInterface {
	
	/**
	 * 向设备发送信息
	 * @param code 设备编号
	 * @param msg 发送内容
	 * @return
	 */
	public String sendMsg(String rtuid, String msg) ;
	
	/**
	 * 接收消息解析
	 * @param bytes
	 * @return
	 */
	public String buildMsg(byte[] bytes);
	
	/**
	 * 开关控制
	 * @param rtuid	主设备id
	 * @param equipmentNum 设备ID
	 * @param lineNum	线路号
	 * @param type	操作类型 0关 1开
	 * @param time  延时时间
	 */
	public void switchControl(String rtuid, int equipmentNum, Integer lineNum, Integer type, long time) throws Exception;
	
	/**
	 * 获取设备状态
	 * @param rtuid	设备ID
	 * @return
	 */
	public Map<String, Object> getState(String rtuid);
}
