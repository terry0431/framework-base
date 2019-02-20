/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.quartz.jobs.bundle.zhyy;

import com.os.framework.core.util.DateUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.cache.bundle.zhyy.SystemCache;
import com.os.framework.web.service.bundle.zhyy.DataBuilder;
import com.os.framework.web.socket.NIOServer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Administrator
 */
public class Shuizhi implements Job {
    SystemCache scache = new SystemCache();
    public static boolean initflag = true;
    NIOServer nserver = new NIOServer();
    private final int sleeptime = 10000;
    private static List<Map<Integer, ArrayList<Map>>> setdata = null;
    private static Map<String,Map<Integer, ArrayList<Map>>> rtukv = new HashMap();
    //服务启动时 获取设置表  
    @Override
    public void execute(JobExecutionContext context) {

        try {
            Calendar calemdar = Calendar.getInstance();
            final int mm = calemdar.get(Calendar.MINUTE);

            if (setdata != null && setdata.size() > 0) {
                //添加线程  多个rtu同时执行 执行一轮睡眠10秒
                for (final Map<Integer, ArrayList<Map>> m : setdata) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                if(m.get(mm) != null && m.get(mm).size() > 0){
                                    for (Map scmap : m.get(mm)) {
                                        controKG(scmap.get("zhyy_rtu_id").toString(), (Integer) scmap.get("s_do_num"), scmap.get("s_flag").toString());
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
        System.out.println("shuiceng " + new Date() + " : " + rtuid + " ================  : " + "#SETDO," + (donum ) + ","+flag+";");
        nserver.doWrite(rtuid, "#SETDO," + (donum ) + "," + flag + ";");
    }

    public void initSCSet() {
        MainDao dao = new MainDao();
        if (setdata == null) {
            //for  rtuid 
            //  key s_time value donum sflag
            setdata = new ArrayList();
            String sql = "select * from zhyy_rtu";
            List<Map<String, Object>> list = dao.queryForList(sql, null);
            //循环全部rtu
            int tmp_m = -1;
            List tmplist = new ArrayList();
            Map tmpmap = new HashMap();
            for (Map m : list) {
                sql = "select * from zhyy_shuiceng_shezhi where zhyy_rtu_id = ? order by s_time";
                List<Map<String, Object>> l = dao.queryForList(sql, new Object[]{m.get("id")});
                for (Map sc : l) {
                    if (tmp_m == -1) {
                        tmp_m = (Integer) sc.get("s_time");
                    }
                    if (tmp_m != (Integer) sc.get("s_time")) {
                        //add 
                        tmpmap.put(tmp_m, tmplist);
                        tmplist = new ArrayList();
                        tmp_m = (Integer) sc.get("s_time");
                    }
                    tmplist.add(sc);
                }
                if(tmplist.size() > 0){
                    tmpmap.put(tmp_m, tmplist);
                }
                setdata.add(tmpmap);
                rtukv.put(m.get("id").toString(), tmpmap);
                tmp_m = -1;
                tmplist = new ArrayList();
                tmpmap = new HashMap();
            }
        }
    }
    
    public void initShuicengDo(String rtuid){
        Calendar calendar = Calendar.getInstance();
        DataBuilder dbuider = new DataBuilder();
        int mm = calendar.get(Calendar.MINUTE);
        int ftime = 0; // 0分
        int ztime = 0;
        if(rtukv.get(rtuid) != null){
            Map<Integer, ArrayList<Map>> mlist = rtukv.get(rtuid);
            for(int sctime : mlist.keySet()){
                if(mm >= sctime){
                    ztime = sctime;
                }else{
                    break;
                }
            }
            System.out.println("ztime : " + ztime);
            try{
                List<Map<String,Object>> szlist = scache.getRtuszByRtuid(rtuid);
                if(szlist != null && szlist.size() > 0){
                    for(Map m : szlist){
                        if(m.get("s_attr").toString().equals("s_shuiceng_kg")){
                            controKG(rtuid, (Integer) m.get("s_tongdao"), "OFF");
                            Thread.sleep(sleeptime);
                        }
                    }
                }
                for (Map scmap : mlist.get(ztime)) {   
                    if(scmap.get("s_flag").toString().equals("ON")){
                        controKG(scmap.get("zhyy_rtu_id").toString(), (Integer) scmap.get("s_do_num"), scmap.get("s_flag").toString());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
