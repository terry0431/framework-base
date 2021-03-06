package com.os.framework.web.handler.zhyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.os.framework.core.util.BinaryConversionUtil;

import com.os.framework.web.queue.zhyy.MsgDelayQueue;
import com.os.framework.web.queue.zhyy.MsgDelayed;
import com.os.framework.core.util.crc.CRCUtil;
import com.os.framework.web.socket.EContorlServer;

public class EContorlPoolModelJMRtuAdapter implements EContorlAdapterInterface {

	MsgDelayed delayerd = null;
	@Override
	public String sendMsg(String rtuid, String msg) {
		if(msg.isEmpty() ) {
			return "-2";
		}
		// TODO Auto-generated method stub
		msg = msg.trim();
		msg = msg.replaceAll(" ", "");
		byte[] bytes = CRCUtil.hexToByteArray(msg);
		msg += CRCUtil.getCRC3(bytes);
		final String data = msg;
		try {
			EContorlServer eserver = new EContorlServer();
			eserver.doWrite(rtuid, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
		return "1";
		//return "<br/>send: " + data + " successed";
	}

	@Override
	public String buildMsg(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void switchControl(String rtuid, int equipmentNum,Integer lineNum, Integer type,long time) throws Exception{
		// TODO Auto-generated method stub
		int j = 0;
		String type1 = "FF00";	//打开命令码
		String type0 = "0000";  //关闭命令码
		String equipmentStr = ""; //设备号
		String lineStr = "";//通道号
		String msg = "";	//发送消息内容
		String str16 = "";	//16进制

		if(equipmentNum < 10 ){
			equipmentStr = "0" + (equipmentNum + 1) + "05";
		}else{

			str16 = Integer.toHexString(equipmentNum);
			if(str16.length() == 1){
				str16 = "0" + str16;
			}
			equipmentStr = str16 + "05";
		}
		if(lineNum < 10){
			lineStr = "000" + lineNum;
		}else{
			str16 = Integer.toHexString(lineNum);
			if(str16.length() == 1){
				str16 = "0" + str16;
			}
			lineStr = "00" + str16;
		}
		if(type == 1){
			msg = equipmentStr + lineStr + type1;
		}else{
			msg = equipmentStr + lineStr + type0;
		}
		Map<String,String> m = new HashMap();
		m.put(rtuid, msg);
		//加入队列

		//MsgQueue.getInstance().addMsg(rtuid, m);
		delayerd = new MsgDelayed(time, m);
		MsgDelayQueue.getInstance().offer(rtuid, delayerd);
		//MsgQueue.getInstance().offer(rtuid, m);
		//直接发送消息
		//sendMsg(rtuid,msg);
		return ;
	}


	@Override
	public Map<String, Object> getState(String rtuid) {
		//EContorlServer eserver = new EContorlServer();
		List<Integer> sblist = new ArrayList();
		sblist.add(8 - 2); // 第一个设备 前2个通路被总控制占用了 池塘开关设备顺序从3开始
		sblist.add(12); //第2个设备
		String sb = "";
		String td = "";
		String flag = "0000";
		String msg = "";
		for (int i = 0; i < sblist.size(); i++) {//设备循环
			if((i+1) < 10){
				sb = "0" + (i+1) + "01";
			}else{
				sb = BinaryConversionUtil.intToHex(i + 1) + "01";
			}
			if(i == 0){
				td = "00" + BinaryConversionUtil.intToHex(sblist.get(i) + 2 );
			}else{
				td = "00" + BinaryConversionUtil.intToHex(sblist.get(i) );
			}

			msg = sb + flag + td ;

//			byte[] bytes = CRCUtil.hexToByteArray(msg);
//			msg += CRCUtil.getCRC3(bytes);
			try{
				Map<String,String> m = new HashMap();
				m.put(rtuid, msg);
				//加入队列
				delayerd = new MsgDelayed(0, m);
				MsgDelayQueue.getInstance().offer(rtuid, delayerd);

				//                	eserver.doWrite("hongyuan", msg);
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		return null;
	}

}
