package com.os.framework.rmi;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangbo
 * @date 2017-1-3 10:35:24
 */
public class RMITest {
	public static void main(String argd[]){
		try{
			HashMap map = new HashMap();
			map.put("arg1", "参数值");
			IRMI irmi = (IRMI)Naming.lookup("rmi://192.168.1.201:10004/RMIServer") ;
			Map m  = irmi.execute(map, "com.os.framework.rmi.Demo", "run");
			
			System.out.println("返回值：" + m.get("arg1"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}