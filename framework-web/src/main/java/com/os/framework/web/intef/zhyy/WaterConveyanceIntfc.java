/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.web.intef.zhyy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.vo.zhyy.Job;
import com.os.framework.web.handler.zhyy.WaterConveyanceHandler;
import com.os.framework.web.handler.zhyy.EContorlAdapterInterface;
import com.os.framework.web.handler.zhyy.EContorlPoolModelJMRtuAdapter;
import com.os.framework.web.socket.EContorlServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Administrator
 */
@Controller
public class WaterConveyanceIntfc {
    static private final byte[] LOCK = new byte[0];

    public static List<Integer> sb = new ArrayList();

    public static Map<Integer, Integer[]> sbztmap = new HashMap();

    public WaterConveyanceIntfc() {
        sb.add(8 - 2); //主机1  前2个口为饵料池用开关 后6
        sb.add(12);
    }

    @RequestMapping(value = {"/ifs/econtrol/econtrol/getData"}, method = {POST})
    @ResponseBody
    public String getData(HttpServletRequest request) {
//        NIOServer.data = "";
        String data = EContorlServer.data;
        return data;
    }


//    @RequestMapping(value = {"/ifs/econtrol/econtrol/getElc"}, method = {POST})
//    @ResponseBody
//    public List getElc(String flag,HttpServletRequest request) { //获取饵料池水深
//        List<Integer> list = new ArrayList();
//        if(flag != null && flag.equals("1")){
//            list.add(6);
//            list.add(6);
//        }else{
//
//        }
//        return list;
//    }

    /**
     * @param request
     * @Description: 获取开关状态
     * @return:java.util.Map<java.lang.Integer,java.lang.Integer[]>
     * @Author:wangbo
     * @Date:2019-02-21
     * @Time:10:29
     **/
    @RequestMapping(value = {"/ifs/econtrol/econtrol/getKgFlag"}, method = {POST})
    @ResponseBody
    public Map<Integer, Integer[]> getKgFlag(HttpServletRequest request) {
        //测试数据
//        if (sbztmap.get(0) == null) {
//            Integer fnum[] = new Integer[]{0, 1, 0, 1, 0, 1};
//            sbztmap.put(0, fnum);
//        }
        return sbztmap;
    }

    /**
     * @param josnStr Job json
     * @Description:执行任务
     * @return:
     * @Author:wangbo
     * @Date:2019-02-21
     * @Time:10:27
     **/
    @RequestMapping(value = {"/ifs/econtrol/econtrol/runJob"}, method = {POST})
    @ResponseBody
    public String runJob(String josnStr) {
        ObjectMapper mapper = new ObjectMapper();
        String yzcid = "hongyuan"; //之后从session中获取
        WaterConveyanceHandler jobbean = new WaterConveyanceHandler();
        Job job = null;
        try {
            if (josnStr != null && !josnStr.equals("")) {
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
            if (WaterConveyanceHandler.getJob(yzcid) != null && WaterConveyanceHandler.getJob(yzcid).isRun_state()) {
                return "-1"; // 当前有任务正在运行
            } else {
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


    @RequestMapping(value = {"/ifs/econtrol/econtrol/sendData"}, method = {POST})
    @ResponseBody
    public String sendData(String rtuid, int lineNum, int equipmentNum, int type, HttpServletRequest request) {

        EContorlAdapterInterface eContorlAdapterInterface = new EContorlPoolModelJMRtuAdapter();
        try {
            eContorlAdapterInterface.switchControl(rtuid, equipmentNum, lineNum, type, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "-1";
        }
        return "1";
    }

    @RequestMapping(value = {"/ifs/econtrol/econtrol/saveLog"}, method = {POST})
    @ResponseBody
    public void saveLog(String ctno, String time, HttpServletRequest request) {
        MainDao dao = new MainDao();
        Map map = new HashMap();
        map.put("sys_time", new Date());
        map.put("c_chitang", ctno);
        map.put("c_shijian", time);
        dao.save("con_log", map, PKBean.NATIVE);
    }

    @RequestMapping(value = {"/ifs/econtrol/econtrol/getJob"}, method = {POST})
    @ResponseBody
    /**
     * @Description:获取任务
     * @param rtuid
     * @param request
     * @return:com.os.framework.web.bean.bundle.zhyy.pool.Job
     * @Author:wangbo
     * @Date:2019-02-21
     * @Time:10:26
     **/
    public Job getJob(String rtuid, HttpServletRequest request) {
        Job job = WaterConveyanceHandler.getJob(rtuid);
        if (job != null) {
            job.setCurrenttime(System.currentTimeMillis());
        }
        return job;
    }

    /**
     * @param zj   主机号
     * @param flag 状态码
     * @Description: 处理并更新状态码
     * @return:
     * @Author:wangbo
     * @Date:2019-02-21
     * @Time:14:21
     **/
    public void updateSbflag(Integer zj, String flag) {
        Integer[] fnum;
        if (flag.length() == 16) {
            flag = flag.substring(12, 16) + flag.substring(0, 8);
        }
        fnum = new Integer[flag.length()];
        for (int i = 0; i < flag.length(); i++) {
            fnum[i] = Integer.parseInt(flag.substring(i, i + 1));
        }
        sbztmap.put(zj, fnum);//更新设备状态
    }


    private static long lastruntime = 0;

    /**
     * @param rtuid
     * @Description:加载开关状态 当有客户连接时多客户端同时每秒提交一次请求 该方法每1秒读取一次状态
     * @return:
     * @Author:wangbo
     * @Date:2019-02-21
     * @Time:10:37
     **/
    @RequestMapping(value = {"/ifs/econtrol/econtrol/loadState"}, method = {POST})
    @ResponseBody
    public void loadState(String rtuid) {
        long intervalTime = 2000;
        EContorlAdapterInterface eContorlAdapterInterface = new EContorlPoolModelJMRtuAdapter();
        // 上次执行时间距今大于 间隔时间
        if (lastruntime == 0 || System.currentTimeMillis() - lastruntime > intervalTime) {
            lastruntime = System.currentTimeMillis();
            eContorlAdapterInterface.getState(rtuid);
        } else {
            return;
        }
    }
}
