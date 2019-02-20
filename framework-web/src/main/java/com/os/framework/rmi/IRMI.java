package com.os.framework.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: wangbo Date: 2011-11-30 Time: 14:58:52 远程调用接口
 */
public interface IRMI extends Remote {

	/**
	 * 提供远程调用服务
	 *
	 * @param map 执行用参数
	 * @param className 执行类
	 * @param methodName 执行函数
	 * @return
	 * @throws RemoteException
	 */
	public HashMap execute(HashMap map, String className, String methodName) throws RemoteException, ClassNotFoundException, NoSuchMethodException, Exception;

}
