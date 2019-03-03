package com.os.framework.quartz.jobs.bundle.zhyy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.core.util.sms.SMSBean;
import com.os.framework.core.util.url.URLUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ShebeiJiankong implements Job{
    static String[] lxr = null;
    static String[] mobile = null;
    public void execute(JobExecutionContext context)
                        throws JobExecutionException {
        MainDao dao = new MainDao();
        URLUtil urlUtil = new URLUtil();
        String url = "http://60.205.204.151:6006/?dev_id=";

        int sbnum = 22;
        String data = "";
        //JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);
        Map dataobj = null;
        Date date = null;
        Random random = new Random();
        Map<String, Object> mapJson = null;

        Calendar nowdate = Calendar.getInstance();
        Integer year = nowdate.get(Calendar.YEAR);
        Integer month = nowdate.get(Calendar.MONTH) + 1;
        Integer day = nowdate.get(Calendar.DAY_OF_MONTH);
        Integer hour = nowdate.get(Calendar.HOUR_OF_DAY);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String sql = "select * from zhyy_shebeijiankong where s_year=? and s_month=? and s_day=? and s_hour=? and zhyy_yangzhichang_id=1";
            List<Map<String, Object>> datalist = dao.queryForList(sql, new Object[]{year, month, day, hour});
            if (datalist == null || datalist.size() == 0) {
                for (int i = 1; i <= sbnum; i++) {
                    data = urlUtil.readFileByUrl(url + i);
                    //jsonObject = JSONObject.fromObject(data);
                    //mapJson = JSONObject.fromObject(jsonObject);
                    mapJson = mapper.readValue(data, Map.class);

                    System.out.println(i + "号  quality : " + mapJson.get("quality"));
                    //System.out.println("timeStamp : " + mapJson.get("timeStamp"));

                    //System.out.println("list 0 : " + list.get(0) );
                    dataobj = new HashMap<String, Object>();
                    dataobj.put("systime", new Date());
                    dataobj.put("zhyy_yangzhichang_id", "1");
                    dataobj.put("zhyy_yangzhiqu_id", i);
                    dataobj.put("s_year", year);
                    dataobj.put("s_month", month);
                    dataobj.put("s_day", day);
                    dataobj.put("s_hour", hour);
                    if (mapJson.get("quality") != null && mapJson.get("quality").toString().equals("1")) {
                        List list = (List) mapJson.get("data");
                        dataobj.put("is_youxiao", mapJson.get("quality"));
                        //2018-03-07T11:16:57.0983636+08:00
                        //date = new Date(mapJson.get("timeStamp").toString());
                        String s = mapJson.get("timeStamp").toString().substring(0, 19);
                        s = s.replaceAll("T", " ");
                        Date d = DateUtil.convertStringToDate(s, "yyyy-MM-dd hh:mm:ss");
                        dataobj.put("s_caijishijian", d);
                        dataobj.put("s_dianliu", list.get(0));
                        dataobj.put("s_bengzhuangtai", list.get(1));
                        dataobj.put("s_weizhi", list.get(2));
                        dataobj.put("s_tongxunzhuangtai", list.get(3));
                        dataobj.put("s_caozuoleixing", list.get(4));
                        dataobj.put("s_shuchu", list.get(5));
                        dataobj.put("s_baojing", list.get(6));
                        dataobj.put("s_flag", list.get(6));
                    } else {
                        dataobj.put("is_youxiao", mapJson.get("quality"));
                        dataobj.put("s_baojing", 1);
                        dataobj.put("s_flag", 2);
                    }
                    try {
                        dao.save("zhyy_shebeijiankong", dataobj, PKBean.NATIVE);
                        if(dataobj.get("s_baojing").toString().equals("-1") && dataobj.get("is_youxiao").toString().equals("1")){
                            //报警短信
                            if(lxr == null || mobile == null){
                                sql = "select * from zhyy_yangzhichang where id = 1";
                                Map map = dao.queryForMap(sql, null);
                                lxr = map.get("y_lianxiren").toString().split(";");
                                mobile = map.get("y_lianxidianhua").toString().split(";");
                            }
                            if(lxr != null && lxr.length > 0){
                                for(String s : lxr){
                                    SMSBean.SendSms(s, "固德水产有效公司" + i + "号换水泵设备出现故障。" + DateUtil.convertDatetimeToString(new Date() ) );
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                for (Map m : datalist) {
                    if (m.get("is_youxiao").toString().equals("1")) {
                        continue;
                    } else {
                        data = urlUtil.readFileByUrl(url + m.get("zhyy_yangzhiqu_id"));
                        
                        mapJson = mapper.readValue(data, Map.class);;

                        System.out.println(m.get("zhyy_yangzhiqu_id") + "号  quality : " + mapJson.get("quality"));
                        //System.out.println("timeStamp : " + mapJson.get("timeStamp"));

                        //System.out.println("list 0 : " + list.get(0) );
                        m.put("systime", new Date());

                        if (mapJson.get("quality") != null && mapJson.get("quality").toString().equals("1")) {
                            List list = (List) mapJson.get("data");
                            m.put("is_youxiao", mapJson.get("quality"));
                            //2018-03-07T11:16:57.0983636+08:00
                            //date = new Date(mapJson.get("timeStamp").toString());
                            String s = mapJson.get("timeStamp").toString().substring(0, 19);
                            s = s.replaceAll("T", " ");
                            Date d = DateUtil.convertStringToDate(s, "yyyy-MM-dd hh:mm:ss");
                            m.put("s_caijishijian", d);
                            m.put("s_dianliu", list.get(0));
                            m.put("s_bengzhuangtai", list.get(1));
                            m.put("s_weizhi", list.get(2));
                            m.put("s_tongxunzhuangtai", list.get(3));
                            m.put("s_caozuoleixing", list.get(4));
                            m.put("s_shuchu", list.get(5));
                            m.put("s_baojing", list.get(6));
                            dataobj.put("s_flag", list.get(6));
                            dao.update("zhyy_shebeijiankong", m);

                        }
                    }

                }
            }
        } catch (Exception e) {
        }
    }
}
