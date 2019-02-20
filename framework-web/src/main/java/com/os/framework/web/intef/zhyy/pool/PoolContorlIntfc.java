/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.web.intef.zhyy.pool;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.web.bean.bundle.zhyy.pool.Job;
import com.os.framework.web.bean.bundle.zhyy.pool.JobBean;
import com.os.framework.web.socket.EContorlServer;
import com.os.framework.web.socket.NIOServer;
import com.os.framework.web.util.crc.CRCUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
public class PoolContorlIntfc {
    static private final byte[] LOCK = new byte[0];
    
    public static List<Integer> sb = new ArrayList();
    
    public static Map<Integer,Integer[]> sbztmap = new HashMap();
    
    public PoolContorlIntfc(){
        sb.add(8-2); //主机1  前2个口为饵料池用开关 后6
        sb.add(12);
    }
    
    @RequestMapping(value = {"/ifs/econtrol/econtrol/getData"}, method = {POST})
    @ResponseBody
    public String getData(HttpServletRequest request) {
        String data  = EContorlServer.data;
//        NIOServer.data = "";
        return data;
    }
//    private static int e1 = 6;
//    private static int e2 = 6;
    @RequestMapping(value = {"/ifs/econtrol/econtrol/getElc"}, method = {POST})
    @ResponseBody
    public List getElc(String flag,HttpServletRequest request) { //获取饵料池水深
        List<Integer> list = new ArrayList();
        if(flag != null && flag.equals("1")){
            list.add(6);
            list.add(6);
        }else{
            
        }
        return list;
    }
    
    @RequestMapping(value = {"/ifs/econtrol/econtrol/getKgFlag"}, method = {POST})
    @ResponseBody
    public Map<Integer,Integer[]> getKgFlag(HttpServletRequest request) { //获取饵料池水深
    	//测试数据
    	if(sbztmap.get(1) == null) {
    		Integer fnum[] = new Integer[] {0,1,0,1,0,1};
    		sbztmap.put(1, fnum);
    	}
        return sbztmap;
    }

    /**
     * @return
     */
    @RequestMapping(value = {"/ifs/econtrol/econtrol/runJob"}, method = {POST})
    @ResponseBody
    public String runJob() {
        return runJob();
    }





    /**
      * @Description:执行输水任务
      * @Param:* @param josnStr Job json
      * @return:java.lang.String 成功返回1  出错返回-1
      * @Author:wangbo
      * @Date:2019-02-20
      * @Time:16:32
    **/
    @RequestMapping(value = {"/ifs/econtrol/econtrol/runJob"}, method = {POST})
    @ResponseBody
    public String runJob(String josnStr) {
    	ObjectMapper mapper = new ObjectMapper();
    	String yzcid = "hongyuan"; //之后从session中获取
    	JobBean jobbean = new JobBean();
    	Job job = null;
    	try {
			if(josnStr != null && !josnStr.equals("")) {
				job = mapper.readValue(josnStr, Job.class);
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	synchronized (yzcid) {
	    	if(JobBean.getJob(yzcid) != null && JobBean.getJob(yzcid).isRun_state()) {
	    		return "-1"; // 当前有任务正在运行
	    	}else{
	        	try {
	        		job.setRun_state(true);
					jobbean.setJob(job, yzcid);
					return jobbean.runJob(yzcid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "-2";
				}
	        	
	    	}
    	}
    }
    @RequestMapping(value = {"/ifs/econtrol/econtrol/getJob"}, method = {POST})
    @ResponseBody
    public Job getJob(String rtuid, HttpServletRequest request) {
        Job job = JobBean.getJob(rtuid);
        if(job != null) {
        	job.setCurrenttime(System.currentTimeMillis());
        }
        return job; 
    }
    @RequestMapping(value = {"/ifs/econtrol/econtrol/sendData"}, method = {POST})
    @ResponseBody
    public String sendData(String data, HttpServletRequest request) {
        
        data = data.trim();
        data = data.replaceAll(" ", "");
        byte[] bytes = CRCUtil.hexToByteArray(data);
        data += CRCUtil.getCRC3(bytes);
        final String msg = data;

        try {

            NIOServer nserver = new NIOServer();
            nserver.doWrite("hongyuan", msg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "<br/>send: " + data + " successed";
    }
    
    @RequestMapping(value = {"/ifs/econtrol/econtrol/saveLog"}, method = {POST})
    @ResponseBody
    public void saveLog(String ctno,String time,HttpServletRequest request) {
        MainDao dao = new MainDao();
        Map map = new HashMap();
        map.put("sys_time", new Date() );
        map.put("c_chitang", ctno);
        map.put("c_shijian", time);
        dao.save("con_log", map, PKBean.NATIVE);
    }
    
    public void updateSbflag(Integer zj,String flag){
        Integer[] fnum;
        if(flag.length() == 16){
           flag = flag.substring(12,16) + flag.substring(0,8);
        }
        fnum = new Integer[flag.length()];
        for(int i = 0;i < flag.length();i ++){
            fnum[i] = Integer.parseInt(flag.substring(i,i+1));
        }
        sbztmap.put(zj, fnum);
    }
}
