package com.os.framework.rmi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: wangbo Date: 2011-11-30 Time: 15:49:46 To
 * change this template use File | Settings | File Templates.
 */
public class Demo {

	public Map run(Map map) {
		RMIServer server = new RMIServer();
		//server.run();
		System.out.println("------------ run demo ------------------" + map.get("arg1"));
		map = new HashMap();
		map.put("arg1", "revalue");
		return map;
	}
}
