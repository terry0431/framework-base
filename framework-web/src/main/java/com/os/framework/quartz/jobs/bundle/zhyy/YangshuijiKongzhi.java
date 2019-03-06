/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.quartz.jobs.bundle.zhyy;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.handler.zhyy.RTUHandler;
import com.os.framework.web.socket.NIOServer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 *
 * @author Administrator
 */
public class YangshuijiKongzhi implements Job {

    public static boolean initflag = true;
    NIOServer nserver = new NIOServer();
    private final int sleeptime = 10000;
    private static List<Map<Integer, ArrayList<Map>>> setdata = null;

    //服务启动时 获取设置表  
    @Override
    public void execute(JobExecutionContext context) {

        try {
            Calendar calemdar = Calendar.getInstance();
            final int hh = calemdar.get(Calendar.HOUR_OF_DAY);

            if (setdata != null && setdata.size() > 0) {
                //添加线程  多个rtu同时执行 执行一轮睡眠10秒
                for (final Map<Integer, ArrayList<Map>> m : setdata) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                if(m.get(hh) != null && m.get(hh).size() > 0){
                                    for (Map ysjmap : m.get(hh)) {
                                        controKG(ysjmap.get("zhyy_rtu_id").toString(), (Integer) ysjmap.get("y_do_num"), ysjmap.get("y_flag").toString());
                                        Thread.sleep(sleeptime);
                                    }
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void controKG(String rtuid, int donum, String flag) throws Exception {
        System.out.println("yangshuiji " + new Date() + " : " + rtuid + " ================  : " + "#SETDO," + (donum ) + ","+flag+";");
        RTUHandler.sendMsg(rtuid, "#SETDO," + (donum ) + "," + flag + ";");
    }

    public void initYSJSet() {
        MainDao dao = new MainDao();
        int tmp_h = -1;
        if (setdata == null) {
            //for  rtuid 
            //  key s_time value donum sflag
            setdata = new ArrayList();
            String sql = "select * from zhyy_rtu";
            List<Map<String, Object>> list = dao.queryForList(sql, null);
            //循环全部rtu
            List tmplist = new ArrayList();
            Map tmpmap = new HashMap();
            for (Map m : list) {
                sql = "select * from zhyy_yangshuiji_shezhi where zhyy_rtu_id = ? order by y_time";
                List<Map<String, Object>> l = dao.queryForList(sql, new Object[]{m.get("id")});
                for (Map ysj : l) {
                    if (tmp_h == -1) {
                        tmp_h = (Integer) ysj.get("y_time");
                    }
                    if (tmp_h != (Integer) ysj.get("y_time")) {
                        //add 
                        tmpmap.put(tmp_h, tmplist);
                        tmplist = new ArrayList();
                        tmp_h = (Integer) ysj.get("y_time");
                    }
                    tmplist.add(ysj);
                }
                
                if(tmplist.size() > 0){
                    tmpmap.put(tmp_h, tmplist);
                }
                setdata.add(tmpmap);
                tmp_h = -1;
                tmplist = new ArrayList();
                tmpmap = new HashMap();
            }
        }
    }
}
