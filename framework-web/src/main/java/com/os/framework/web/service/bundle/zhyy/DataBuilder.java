package com.os.framework.web.service.bundle.zhyy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.DateUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import com.os.framework.web.cache.bundle.zhyy.SystemCache;
import com.os.framework.web.socket.NIOServer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.quartz.JobExecutionContext;

/**
 *
 * @author Administrator
 */
public class DataBuilder {

//    private static Map<String, List<Map<String, Object>>> rtushezhi = new HashMap();//rtu设置
    NIOServer nserver = new NIOServer();
//    static Map<String,String> sbyx_ktmap = new HashMap();//设备运行情况数据最新更新时间

//    public List<Map<String, Object>> getRtuSzLsit(String rtuid) {
//        return rtushezhi.get(rtuid);
//    }
    SystemCache sysCache = new SystemCache();
    public void saveRtuData(RTUMod rtumod) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao dao = new MainDao();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DateUtil.convertStringToDatetime(rtumod.getDatatime()));
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
//            if (rtushezhi.get(rtumod.getRtuid()) == null) {
//                rtushezhi.put(rtumod.getRtuid(), dao.queryForList("select * from zhyy_rtu_shezhi where zhyy_rtu_id=? order by zhyy_yangzhiqu_id", new Object[]{rtumod.getRtuid()}));
//            }
            Date date = DateUtil.convertStringToDatetime(rtumod.getDatatime());
            rtumod.setLongtime(date.getTime() + "");
//            Map sbyunxing_map = new HashMap();
            Map rtumap = new HashMap();

            String yangzhiquid = "";
            String sql = "";
            boolean szflag = false;
            List<Map> rtushezhi = sysCache.getRtuszByRtuid(rtumod.getRtuid());
            for (Map m : rtushezhi ) {
                // 保存设备状况数据
                if (m.get("s_leibie").equals("ai")) {
                    rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtuai().get((Integer) m.get("s_tongdao") - 1).get("va"));
                } else if (m.get("s_leibie").equals("di")) {
                    rtumap.put("di_" + m.get("s_tongdao"), rtumod.getRtudi().get((Integer) m.get("s_tongdao") - 1).get("va"));
                } else {
                    rtumap.put("do_" + m.get("s_tongdao"), rtumod.getRtudo().get((Integer) m.get("s_tongdao") - 1).get("va"));
                }
            }
            System.out.println("============ zhyy_rtu_data save ==========");
            rtumap.put("zhyy_rtu_id", rtumod.getRtuid());
            rtumap.put("r_systime", new Date());
            rtumap.put("r_caijishijian", rtumod.getDatatime());
            rtumap.put("r_year", year);
            rtumap.put("r_month", month);
            rtumap.put("r_day", day);
            dao.save("zhyy_rtu_data", rtumap, PKBean.NATIVE);
            //保存各养殖区水质数据
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void builddata(String rtuid) {
        Calendar calendar = Calendar.getInstance();
        MainDao dao = new MainDao();
        SystemCache syscache = new SystemCache();
        List<RTUMod> RTUModList = null;
        Map<String, Map<String, Object>> szdatamap = new HashMap(); //key 养殖区+水层 value 对象数据
        Map<String, Integer> szdatanum = new HashMap();// key 养殖区+水层 value 计总数 用于最后算小时平均值
        Integer[] nums;
//        Map<String,String> sz_ktmap = new HashMap();//水质数据最新更新时间
        List<Map<String, Object>> scshezhi = new ArrayList();
        List<Map<String, Object>> szshezhi = new ArrayList();
        try {
            List<Map<String, Object>> rtushezhi = syscache.getRtuszByRtuid(rtuid);
            scshezhi.addAll(rtushezhi );
            scshezhi.removeIf(new Predicate<Map>() {
                @Override
                public boolean test(Map mm) {
                    return !mm.get("s_attr").equals("s_shuiceng_kg");
                }
            });
            szshezhi.addAll(rtushezhi);
            szshezhi.removeIf(new Predicate<Map>() {
                @Override
                public boolean test(Map mm) {
                    return !mm.get("s_obj").equals("zhyy_shuizhishuju");
                }
            });
            RTUModList = NIOServer.hourDateMap.get(rtuid);
            double d1 = 0d;
            double d2 = 0d;
            int num;
            for (RTUMod rtumod : RTUModList) {

                calendar.setTime(DateUtil.convertStringToDatetime(rtumod.getDatatime()));
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                int hour = calendar.get(Calendar.HOUR_OF_DAY) ;

                Date date = DateUtil.convertStringToDatetime(rtumod.getDatatime());
                rtumod.setLongtime(date.getTime() + "");
                Map shuizhi_map = new HashMap();
                //            Map sbyunxing_map = new HashMap();
                Map rtumap = new HashMap();

                String yangzhiquid = "";
                String sql = "";
                boolean szflag = false;

                for (Map m : rtushezhi) {
                
                    // 保存设备状况数据
                    if (m.get("s_leibie").equals("ai")) {
                        rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtuai().get((Integer) m.get("s_tongdao") - 1).get("va"));
                    } else if (m.get("s_leibie").equals("di")) {
                        rtumap.put("di_" + m.get("s_tongdao"), rtumod.getRtudi().get((Integer) m.get("s_tongdao") - 1).get("va"));
                    } else {
                        rtumap.put("do_" + m.get("s_tongdao"), rtumod.getRtudo().get((Integer) m.get("s_tongdao") - 1).get("va"));
                    }

                    if (yangzhiquid.equals("")) {
                        yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
                    }
                    //保存上一个养殖区数据

                    if (!yangzhiquid.equals(m.get("zhyy_yangzhiqu_id").toString())) { //保存上一个养殖区的水质数据
                        if (szflag) { //该养殖区有水质数据
                            shuizhi_map.put("zhyy_yangzhichang_id", rtumod.getRtuid().substring(0, 4));
                            shuizhi_map.put("zhyy_rtu_id", rtumod.getRtuid());
                            shuizhi_map.put("s_caijishijian", rtumod.getDatatime());
                            shuizhi_map.put("s_systime", new Date());
                            shuizhi_map.put("s_year", year);
                            shuizhi_map.put("s_month", month);
                            shuizhi_map.put("s_day", day);
                            shuizhi_map.put("s_hour", hour);
                            if (szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")) != null) {
                                for (Map szmap : szshezhi) {
                                    if (shuizhi_map.get(szmap.get("s_attr")) != null) {
                                        d1 = (Double) shuizhi_map.get(szmap.get("s_attr"));
                                    }
                                    //判断该值是否在正常值范围内 之后在指标设置中加入 有效值范围设定 
                                    if (d1 > 0) {
                                        if (szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).get(szmap.get("s_attr")) != null) {
                                            d2 = (Double) szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).get(szmap.get("s_attr"));
                                        } else {
                                            d2 = 0;
                                        }
                                        szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).put(szmap.get("s_attr").toString(), d1 + d2);
                                        szdatanum.put((shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")), szdatanum.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")) + 1);
                                    }
                                }
                            } else {
                                szdatamap.put(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu"), shuizhi_map);
                                szdatanum.put((shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")), 1);
                            }

                            szflag = false;
                            shuizhi_map = new HashMap();
                            yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
                            //                        }
                        } else {
                            yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
                        }
                    }
                    shuizhi_map.put("zhyy_yangzhiqu_id", m.get("zhyy_yangzhiqu_id"));

                    //                if(m.get("s_obj") != null && !m.get("s_obj").equals("")){ //存储数据表不为空
                    if (m.get("s_obj").equals("zhyy_rtu_data")) {    //设备运行记录表
                        //                        if(m.get("s_leibie").equals("ai")){
                        //                            rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
                        //                        }else 
                        if (m.get("s_leibie").equals("di")) {
                            //                            rtumap.put("di_" + m.get("s_tongdao"), rtumod.getRtudi().get( (Integer)m.get("s_tongdao") - 1 ).get("va"));
                            if (m.get("s_attr").equals("s_caijiqi_flag")) { //水质采集器状态
                                szflag = true;
                                if (rtumod.getRtudi().get((Integer) m.get("s_tongdao") - 1).get("va").toString().equals("1")) {
                                    shuizhi_map.put("s_flag", 1);
                                } else {
                                    shuizhi_map.put("s_flag", 0);
                                }
                            }
                        } else if (m.get("s_leibie").equals("do")) {

                            //水层处理 
                            if (m.get("s_attr").toString().equals("s_shuiceng_kg")) {
                                if (rtumod.getRtudo().get((Integer) m.get("s_tongdao") - 1).get("va").toString().equals("1")) {
                                    szflag = true;
                                    shuizhi_map.put("s_cengshu", (Integer) m.get("s_tongdao"));
                                }
                            }
                        }
                    } else if (m.get("s_obj").equals("zhyy_shuizhishuju")) {    //水质数据表
                        szflag = true;
                        shuizhi_map.put(m.get("s_attr"), rtumod.getRtuai().get((Integer) m.get("s_tongdao") - 1).get("va"));
                        //                        rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtudo().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
                    }
                    //                }
                }
                //保存最后一个养殖区数据
                if (szflag) {
                    //                if (sz_ktmap.get(yangzhiquid) != null && sz_ktmap.get(yangzhiquid).equals(rtumod.getDatatime())) { //该养殖区 该时间 已保存过水质数据
                    //                    dao.update("zhyy_shuizhishuju", shuizhi_map, " where s_caijishijian=? and zhyy_yangzhiqu_id=? ", new Object[]{rtumod.getDatatime(), yangzhiquid});
                    //                } else {
                    shuizhi_map.put("zhyy_rtu_id", rtumod.getRtuid());
                    shuizhi_map.put("zhyy_yangzhichang_id", rtumod.getRtuid().substring(0, 4));
                    shuizhi_map.put("s_caijishijian", rtumod.getDatatime());
                    shuizhi_map.put("s_systime", new Date());
                    shuizhi_map.put("s_year", year);
                    shuizhi_map.put("s_month", month);
                    shuizhi_map.put("s_day", day);
                    shuizhi_map.put("s_hour", hour);
                    if (szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")) != null) {
                        for (Map szmap : szshezhi) {
                            if (shuizhi_map.get(szmap.get("s_attr")) != null) {
                                d1 = (Double) shuizhi_map.get(szmap.get("s_attr"));
                            }
                            //判断该值是否在正常值范围内 之后在指标设置中加入 有效值范围设定 
                            if (d1 > 0) {
                                if (szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).get(szmap.get("s_attr")) != null) {
                                    d2 = (Double) szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).get(szmap.get("s_attr"));
                                } else {
                                    d2 = 0;
                                }
                                szdatamap.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")).put(szmap.get("s_attr").toString(), d1 + d2);
                                
                            }
                        }
                        szdatanum.put((shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")), szdatanum.get(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")) + 1);

                    } else {
                        szdatamap.put(shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu"), shuizhi_map);
                        szdatanum.put((shuizhi_map.get("zhyy_yangzhiqu_id") + "_" + shuizhi_map.get("s_cengshu")), 1);
                    }
                }
            }

            //计算平均值 并 保存
            for (String k : szdatamap.keySet()) {
                for (Map szmap : szshezhi) {
                    if (szdatamap.get(k).get(szmap.get("s_attr")) != null) {
                        d2 = (Double) szdatamap.get(k).get(szmap.get("s_attr"));
                        num = szdatanum.get(k);
                        szdatamap.get(k).put(szmap.get("s_attr").toString(), Double.parseDouble(String.format("%.1f", d2 / num) ));
                    }
                }
                dao.save("zhyy_shuizhishuju", szdatamap.get(k), PKBean.NATIVE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void builddata_10fz(RTUMod rtumod) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao dao = new MainDao();
        Calendar calendar = Calendar.getInstance();
//        Map<String,String> sz_ktmap = new HashMap();//水质数据最新更新时间
        try {
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int month = calendar.get(Calendar.MONTH) + 1;
//            int year = calendar.get(Calendar.YEAR);
            calendar.setTime(DateUtil.convertStringToDatetime(rtumod.getDatatime()));
            int hour = calendar.get(Calendar.HOUR_OF_DAY) ;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
//            if(rtushezhi.get(rtumod.getRtuid()) == null){
//                rtushezhi.put(rtumod.getRtuid(), dao.queryForList("select * from zhyy_rtu_shezhi where zhyy_rtu_id=? order by zhyy_yangzhiqu_id", new Object[]{rtumod.getRtuid()}) );
//            }
            Date date = DateUtil.convertStringToDatetime(rtumod.getDatatime());
            rtumod.setLongtime(date.getTime() + "");
            Map shuizhi_map = new HashMap();
//            Map sbyunxing_map = new HashMap();
            Map rtumap = new HashMap();
            
            String yangzhiquid = "";
            String sql = "";
            boolean szflag = false;
            List<Map> rtushezhi = sysCache.getRtuszByRtuid(rtumod.getRtuid());
            for(Map m : rtushezhi){
                // 保存设备状况数据
//                if(m.get("s_leibie").equals("ai")){
//                    rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
//                }else if(m.get("s_leibie").equals("di")){
//                    rtumap.put("di_" + m.get("s_tongdao"), rtumod.getRtudi().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
//                }else{
//                    rtumap.put("do_" + m.get("s_tongdao"), rtumod.getRtudo().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
//                }
                
                if(yangzhiquid.equals("")){
                    yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
                }
                //保存上一个养殖区数据
                
                if(!yangzhiquid.equals(m.get("zhyy_yangzhiqu_id").toString())){ //保存上一个养殖场的水质数据
                    if(szflag){ //该养殖区有水质数据
//                        if(sz_ktmap.get(yangzhiquid) != null && sz_ktmap.get(yangzhiquid).equals(rtumod.getDatatime() ) ){ //该养殖区 该时间 已保存过水质数据
//                            dao.update("zhyy_shuizhishuju", shuizhi_map, " where s_caijishijian=? and zhyy_yangzhiqu_id=? ", new Object[]{rtumod.getDatatime(),(Integer.valueOf(yangzhiquid) )});
//                            szflag = false;
//                            shuizhi_map = new HashMap();
//                            yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
//                        }else{
                            shuizhi_map.put("zhyy_yangzhichang_id", rtumod.getRtuid().substring(0,4));
                            shuizhi_map.put("zhyy_rtu_id", rtumod.getRtuid());
                            shuizhi_map.put("s_caijishijian", rtumod.getDatatime());
                            shuizhi_map.put("s_systime", new Date());
                            shuizhi_map.put("s_year", year);
                            shuizhi_map.put("s_month", month);
                            shuizhi_map.put("s_day", day);
                            shuizhi_map.put("s_hour", hour);
                            dao.save("zhyy_shuizhishuju", shuizhi_map, PKBean.NATIVE);
//                            sz_ktmap.put(yangzhiquid, rtumod.getDatatime());
                            System.out.println("============ zhyy_shuizhishuju save ==========" + yangzhiquid);
                            szflag = false;
                            shuizhi_map = new HashMap();
                            yangzhiquid = m.get("zhyy_yangzhiqu_id").toString();
//                        }
                    }
                }
                shuizhi_map.put("zhyy_yangzhiqu_id", m.get("zhyy_yangzhiqu_id"));
                
//                if(m.get("s_obj") != null && !m.get("s_obj").equals("")){ //存储数据表不为空
                    if(m.get("s_obj").equals("zhyy_rtu_data")){    //设备运行记录表
//                        if(m.get("s_leibie").equals("ai")){
//                            rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
//                        }else 
                        if(m.get("s_leibie").equals("ai")){
//                            rtumap.put("di_" + m.get("s_tongdao"), rtumod.getRtudi().get( (Integer)m.get("s_tongdao") - 1 ).get("va"));
                            if(m.get("s_attr").toString().indexOf("s_dianliu") > -1){ //水质采集器状态
                                szflag = true;
                                shuizhi_map.put(m.get("s_attr"), rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
//                                if(rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va").toString().equals("1")){
//                                    shuizhi_map.put("s_flag", 1);
//                                }else{
//                                    shuizhi_map.put("s_flag", 0);
//                                }
                            }
                        }
                    }else if(m.get("s_obj").equals("zhyy_shuizhishuju")){    //水质数据表
                        if(m.get("s_attr").equals("s_shuishen")){ //水质采集器状态
                            szflag = true;
                            shuizhi_map.put(m.get("s_attr"), rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
                        }

//                        rtumap.put("ai_" + m.get("s_tongdao"), rtumod.getRtudo().get( (Integer)m.get("s_tongdao") - 1  ).get("va"));
                    }
//                }
            }
            //保存最后一个养殖区数据
            if(szflag){
//                if (sz_ktmap.get(yangzhiquid) != null && sz_ktmap.get(yangzhiquid).equals(rtumod.getDatatime())) { //该养殖区 该时间 已保存过水质数据
//                    dao.update("zhyy_shuizhishuju", shuizhi_map, " where s_caijishijian=? and zhyy_yangzhiqu_id=? ", new Object[]{rtumod.getDatatime(), yangzhiquid});
//                } else {
                    shuizhi_map.put("zhyy_rtu_id", rtumod.getRtuid());
                    shuizhi_map.put("zhyy_yangzhichang_id", rtumod.getRtuid().substring(0,4));
                    shuizhi_map.put("s_caijishijian", rtumod.getDatatime());
                    shuizhi_map.put("s_systime", new Date());  
                    shuizhi_map.put("s_year", year);
                    shuizhi_map.put("s_month", month);
                    shuizhi_map.put("s_day", day);
                    shuizhi_map.put("s_day", day);
                    dao.save("zhyy_shuizhishuju_10fz", shuizhi_map, PKBean.NATIVE);
//                    sz_ktmap.put(yangzhiquid, rtumod.getDatatime());
                    System.out.println("============ last zhyy_shuizhishuju save ==========");
//                }
            }
//            System.out.println("============ zhyy_rtu_data save ==========");
//            rtumap.put("zhyy_rtu_id", rtumod.getRtuid());  
//            rtumap.put("r_systime", new Date());  
//            rtumap.put("r_caijishijian", rtumod.getDatatime());  
//            rtumap.put("r_year", year);
//            rtumap.put("r_month", month);
//            rtumap.put("r_day", day);
//            dao.save("zhyy_rtu_data", rtumap, PKBean.NATIVE);
            //保存各养殖区水质数据
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
