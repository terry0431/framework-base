package com.os.framework.web.service.zhyy;

import com.os.framework.core.util.DateUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import com.os.framework.web.cache.zhyy.SystemCache;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

public class CheckRTUData {
    
//    private static Map<String,List<Map<String ,Object>>> baojingshezhi = null;
    public static Map<String ,Map<String,Map<String,Object> > > bjlog = new HashMap();
    SystemCache scache = new SystemCache();
//    public void init(){
//        baojingshezhi = new HashMap();
//        MainDao dao = new MainDao();
//        String sql = "select * from zhyy_rtu ";
//        List<Map<String,Object>> rtulist = dao.queryForList(sql, null);
//        List bjszlist = null;
//        for(Map rtumap : rtulist){
//            sql = "select a.*,b.s_mingcheng,b.zhyy_yangzhiqu_id,b.s_attr ,d.y_mingcheng,d.y_lianxiren,d.y_lianxidianhua from zhyy_baojingshezhi a "
//                    + " left join zhyy_rtu_shezhi b "
//                    + " on a.zhyy_rtu_id=b.zhyy_rtu_id and a.b_tongdao=b.s_tongdao and a.b_tongdaoleibie=b.s_leibie "
//                    + " left join zhyy_rtu c "
//                    + " on a.zhyy_rtu_id = c.id "
//                    + " left join zhyy_yangzhichang d"
//                    + " on c.zhyy_yangzhichang_id = d.id "
//                    + " where a.zhyy_rtu_id=? ";
//            bjszlist = dao.queryForList(sql, new Object[]{rtumap.get("id")});
//            if(bjszlist != null && bjszlist.size() > 0){
//                baojingshezhi.put(rtumap.get("id").toString(),bjszlist);
//            }
//        }
//    }
    
    public void check(RTUMod rtu) throws Exception{
        MainDao dao = new MainDao();
//        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> warning;
        //Map<String,Boolean[]> bjinfo = new HashMap();
        String val, expression;
        List<Variable> varialbes;
        String content  = "";
        List<Map<String,Object>> bjszlist = scache.getBjsz(rtu.getRtuid());
        if(bjszlist == null || bjszlist.size() == 0){
            return ;
        }
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtil.convertStringToDatetime(rtu.getDatatime().toString());
        calendar.setTime(date);
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        varialbes = new ArrayList<>();
        varialbes.add(Variable.createVariable("date", Integer.parseInt(DateUtil.convertDateToString(date,"yyyyMMdd") )) );
        varialbes.add(Variable.createVariable("time", Integer.parseInt(DateUtil.convertDateToString(date,"hhmmss") )) );
        varialbes.add(Variable.createVariable("d_y", year) );
        varialbes.add(Variable.createVariable("d_m", month) );
        varialbes.add(Variable.createVariable("d_d", day) );
        
        varialbes.add(Variable.createVariable("t_h", hour) );
        varialbes.add(Variable.createVariable("t_m", minute) );
        if(bjszlist != null && bjszlist.size() > 0){
            String sql = "select * from zhyy_xiaoxi where zhyy_baojingshezhi_id=? and x_systime > ?";
            for(Map map : bjszlist){
                if(map.get("b_tongdaoleibie").toString().equals("ai")){   
                    val = rtu.getRtuai().get((Integer)map.get("b_tongdao") - 1).get("va").toString();
                }else if(map.get("b_tongdaoleibie").toString().equals("di")){
                    val = rtu.getRtudi().get((Integer)map.get("b_tongdao") - 1).get("va").toString();
                }else{
                    val = rtu.getRtudo().get((Integer)map.get("b_tongdao") - 1).get("va").toString();
                }
                expression = map.get("b_gongshi").toString();
                varialbes.add(Variable.createVariable("x", Float.valueOf(val) ) );
                if(map.get("s_attr").toString().equals("s_shuishen")){
                    Map m = scache.getYzq(map.get("zhyy_yangzhiqu_id").toString());
                    varialbes.add(Variable.createVariable("y", Float.valueOf(m.get("y_shuishen").toString() ) ) );
                }
//                if(NIOServer.bjinfo.get(map.get("zhyy_yangzhiqu_id").toString() ) == null ){
//                    NIOServer.bjinfo.put(map.get("zhyy_yangzhiqu_id").toString(), new Boolean[]{false,false});
//                }
                if(bjlog.get(map.get("zhyy_yangzhiqu_id").toString()) == null){ 
                    bjlog.put(map.get("zhyy_yangzhiqu_id").toString() , new HashMap() );
                }
                if ("true".equals(ExpressionEvaluator.evaluate(expression, varialbes).toString()) ) {
//                    if(map.get("b_tongdaoleibie").toString().equals("ai")){
//                        if(NIOServer.bjinfo.get(map.get("zhyy_yangzhiqu_id").toString() ) != null ){
//                            NIOServer.bjinfo.get(map.get("zhyy_yangzhiqu_id").toString())[0] =true;
//                        }else{
//                            NIOServer.bjinfo.put(map.get("zhyy_yangzhiqu_id").toString(), new Boolean[]{true,false});
//                        }
//                    }else{
//                        if(NIOServer.bjinfo.get(map.get("zhyy_yangzhiqu_id").toString() ) != null ){
//                            NIOServer.bjinfo.get(map.get("zhyy_yangzhiqu_id").toString())[1] =true;
//                        }else{
//                            NIOServer.bjinfo.put(map.get("zhyy_yangzhiqu_id").toString(), new Boolean[]{false,true});
//                        }
//                    }
                    // 检查 该报警设置 间隔时间内 有没有该报警 已存在的话不再次报警
                    long jgsj = date.getTime() - Long.valueOf( map.get("b_jiangeshijian").toString()) * 60 * 1000l;
                    date.setTime(jgsj);
                    List list = dao.queryForList(sql, new Object[]{map.get("id"),date });
                    
                    warning = new HashMap<>();
                    warning.put("x_systime", rtu.getDatatime());
                    warning.put("x_leibie", map.get("b_leixing").toString());
                    warning.put("zhyy_yangzhichang_id", rtu.getRtuid().substring(0, 4));
                    warning.put("x_jibie", map.get("b_baojingdengji").toString());
                    if(map.get("b_leixing").toString().equals("1")){//设备故障
                        content = map.get("y_mingcheng") + "第" + map.get("zhyy_yangzhiqu_id") +"养殖池的" + map.get("s_mingcheng") + map.get("b_baojingneirong")  ;
                    }else if(map.get("b_leixing").toString().equals("2")){//指标数值异常
                        content = map.get("y_mingcheng") + "第" + map.get("zhyy_yangzhiqu_id") +"养殖池的" + map.get("s_mingcheng") + map.get("b_baojingneirong") + ",当前为" + val;
                    }   
                    warning.put("zhyy_baojingshezhi_id", map.get("id"));
//                    content = map.get("y_mingcheng") + "第" + map.get("zhyy_yangzhiqu_id") +"养殖区的" + map.get("s_mingcheng") + map.get("b_baojingneirong") ;
                    warning.put("x_chuli", 0);
                    warning.put("x_content", content);
                    //保存最新的报警内容  key 养殖区id_通道类别_通道号  value 报警内容map
                    
                    bjlog.get(map.get("zhyy_yangzhiqu_id").toString()).put(rtu.getRtuid() + "_" + map.get("b_tongdaoleibie") + "_" + map.get("b_tongdao"), warning);
                    //间隔时间以内 不保存 不发送相同报警内容
                    if(list != null && list.size() > 0){
                        continue;
                    }
                    //保存消息记录
                    Integer xxid = dao.save("zhyy_xiaoxi", warning, PKBean.NATIVE);
                    warning.put("id", xxid);
                    //发送短信
//                    SMSBean.SendSms(map.get("y_lianxidianhua").toString(), content);
                    //推送消息
                    Message msg = new Message();
                    msg.updateMsg(warning);
//                    warning.put("va", val);
//                    warning.put("lianxiren", map.get("y_lianxiren").toString());
//                    warning.put("lianxidianhua", map.get("y_lianxidianhua").toString());
//                    warning.put("tongdao", map.get("b_tongdao").toString());
//                    list.add(warning);
                    
                }else{
                    bjlog.get(map.get("zhyy_yangzhiqu_id").toString()).put(rtu.getRtuid() + "_" + map.get("b_tongdaoleibie") + "_" + map.get("b_tongdao"), null);
                }
            }
        }
    }
    
}
