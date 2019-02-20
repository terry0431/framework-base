/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.rmi;

import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class RMICommonBean {
    
    @SuppressWarnings("unchecked")
	public Map saveApplicationform(Map map){
    	MainDao mainDao = new MainDao();
    	mainDao.beginTransaction();
    	Map result = new HashMap();
    	try{
            List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("param1");
            String ids = "";
            for(Map<String, Object> m : list){
                if(mainDao.saveOrUpdate("cy_applicationform", m, PKBean.ASSIGNED)){
                    ids += "," + m.get("id");
                }
            }
            System.out.println(ids);
            if(ids.length()>0) ids = ids.substring(1);
            result.put("result1", ids);
            mainDao.commit();
    	}catch (Exception e) {
            mainDao.rollback();
            e.printStackTrace();
        }
        return result;
    }
}
