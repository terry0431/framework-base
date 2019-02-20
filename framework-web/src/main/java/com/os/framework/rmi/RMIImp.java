package com.os.framework.rmi;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: wangbo Date: 2011-11-30 Time: 14:59:09 To
 * change this template use File | Settings | File Templates.
 */
public class RMIImp extends UnicastRemoteObject implements IRMI {

	public RMIImp() throws RemoteException {

	}

	public HashMap execute(HashMap map, String className, String methodName) throws Exception {
		Class c = Class.forName(className);

		Class[] paramaters = new Class[]{Map.class};
		Method method = c.getDeclaredMethod(methodName, paramaters);

		Method m = c.getDeclaredMethod(methodName, paramaters);

		Object[] para = new Object[]{map};

		return (HashMap) method.invoke(c.newInstance(), para);

	}
}
