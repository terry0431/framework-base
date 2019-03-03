package com.os.framework.rmi;

import com.os.framework.core.config.CommonBean;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	private static boolean isStartup = true;
	private static IRMI rmi = null;

	public void run() {
		try {
			rmi = new RMIImp();
			String url = CommonBean.getParamValue("frameworkRMI");
			if (isStartup) {
				LocateRegistry.createRegistry(getPort(url));
				isStartup = false;
			}
			Naming.rebind(url, rmi);
			System.out.println("call rmi server. url:" + url);
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getPort(String url) {
		String[] str = url.split(":");
		return Integer.parseInt(str[2].split("/")[0]);
	}
}
