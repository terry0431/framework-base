package com.os.framework.web.intef.zhyy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.handler.zhyy.RTUHandler;
import com.os.framework.web.socket.NIOServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShebeiJiankongIntfc {

    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/nowlist"}, method = {POST})
    @ResponseBody
    public List nowlist(String yzcid) {
        MainDao dao = new MainDao();
        Calendar rightNow = Calendar.getInstance();
        List list = new ArrayList();
        List<Map<String, Object>> tmplist = null;
        String sql = "select * from zhyy_shebeijiankong where s_year = ? and s_month = ? and s_day = ? and s_hour = ? and zhyy_yangzhichang_id=?";
        int year = rightNow.get(1);
        int month = rightNow.get(2) + 1;
        int day = rightNow.get(5);
        int hour = rightNow.get(11);
        //if ((map.get("hour") == null) || (((String) map.get("hour")).equals(""))) {
            for (int h = 1; h <= rightNow.get(11); h++) {
                tmplist = dao.queryForViewList(sql, new Object[]{Integer.valueOf(year),
                    Integer.valueOf(month),
                    Integer.valueOf(day),
                    Integer.valueOf(h),
                    yzcid});
                list.add(tmplist);
            }
        //}
        return list;
    }

    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/list"}, method = {POST})
    @ResponseBody
    public Map lsit(String arg, int pageNum, int pageSize, int rowTotal) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT  zhyy_shebeijiankong.* ";
//        sql += " ,zhyy_yangzhichang.* ";
//        sql += " ,zhyy_yangzhiqu.* ";
//        sql += " ,zhyy_shebei.* ";
        sql += " FROM zhyy_shebeijiankong zhyy_shebeijiankong ";
//        sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_shebeijiankong.zhyy_yangzhichang_id = zhyy_yangzhichang.id ";
//        sql += " left join zhyy_yangzhiqu zhyy_yangzhiqu on zhyy_shebeijiankong.zhyy_yangzhiqu_id = zhyy_yangzhiqu.id ";
//        sql += " left join zhyy_shebei zhyy_shebei on zhyy_shebeijiankong.zhyy_shebei_id = zhyy_shebei.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_shebeijiankong WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if(arg != null){
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (IOException ex) {
                Logger.getLogger(XuntangIntfc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (map.size() != 0) {
            if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id"))) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and zhyy_shebeijiankong.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
            if (map.containsKey("is_youxiao") && !"".equals(map.get("is_youxiao"))) {
                valuesList.add(map.get("is_youxiao"));
                sql += " and zhyy_shebeijiankong.is_youxiao = ?";
                sqlNum += " and is_youxiao = ?";
            }
            if (map.containsKey("s_caijishijian") && !"".equals(map.get("s_caijishijian"))) {
                valuesList.add(map.get("s_caijishijian"));
                sql += " and zhyy_shebeijiankong.s_caijishijian = ?";
                sqlNum += " and s_caijishijian = ?";
            }
            if (map.containsKey("s_dianliu") && !"".equals(map.get("s_dianliu"))) {
                valuesList.add(map.get("s_dianliu"));
                sql += " and zhyy_shebeijiankong.s_dianliu = ?";
                sqlNum += " and s_dianliu = ?";
            }
            
            if (map.containsKey("s_flag") && !"".equals(map.get("s_flag")) && !map.get("s_flag").equals("-1")) {
                valuesList.add(map.get("s_flag"));
                sql += " and zhyy_shebeijiankong.s_flag = ?";
                sqlNum += " and s_flag = ?";
            }
            if (map.containsKey("s_year") && !"".equals(map.get("s_year"))) {
                valuesList.add(map.get("s_year"));
                sql += " and zhyy_shebeijiankong.s_year = ?";
                sqlNum += " and s_year = ?";
            }
            if (map.containsKey("s_month") && !"".equals(map.get("s_month"))) {
                valuesList.add(map.get("s_month"));
                sql += " and zhyy_shebeijiankong.s_month = ?";
                sqlNum += " and s_month = ?";
            }
            if (map.containsKey("s_day") && !"".equals(map.get("s_day"))) {
                valuesList.add(map.get("s_day"));
                sql += " and zhyy_shebeijiankong.s_day = ?";
                sqlNum += " and s_day = ?";
            }
            if (map.containsKey("s_hour") && !"".equals(map.get("s_hour"))) {
                valuesList.add(map.get("s_hour"));
                sql += " and zhyy_shebeijiankong.s_hour = ?";
                sqlNum += " and s_hour = ?";
            }
            
        }

        sql += " order by zhyy_shebeijiankong.id desc";
        Map mapNum = mainDao.queryForMap(sqlNum, valuesList.toArray());
        List list = mainDao.queryForViewList(sql, valuesList.toArray(), pageNum, pageSize);
        if (rowTotal == -1) {
            rowTotal = Integer.parseInt(mapNum.get("num").toString());
        }
        Map returnMap = new HashMap();
        returnMap.put("dataList", list);
        returnMap.put("rowTotal", rowTotal);
        return returnMap;
    }
    
//    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/getData"}, method = {POST})
//    @ResponseBody
//    public String getData() {
//        return DataBuilder.log;
//    }
    
    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/getJkData"}, method = {POST})
    @ResponseBody
    public Map<String,List<Map<String,Object>>> getJkData(String yzcid) {
        MainDao dao = new MainDao();
        Map<String,List<Map<String,Object>>> jkdata = new HashMap();
        String sql = "select * from zhyy_rtu where zhyy_yangzhichang_id = ?";
        List<Map<String,Object>> rtulist = dao.queryForList(sql, new Object[]{yzcid});
        List<Map<String,Object>> rtuszlist = null;
        for(Map m : rtulist){
            if(RTUHandler.rtumap.get(m.get("id")) != null){
                sql = "select * from zhyy_rtu_shezhi where zhyy_rtu_id = ? order by s_leibie,s_tongdao";
                rtuszlist = dao.queryForList(sql, new Object[]{m.get("id")});
                for(Map szm : rtuszlist){
                        if(szm.get("s_leibie").toString().equals("ai")){
                            szm.put("val", RTUHandler.rtumap.get(m.get("id").toString()).getRtuai().get( (Integer)szm.get("s_tongdao") - 1 ).get("va"));
                        }else if(szm.get("s_leibie").toString().equals("di")){
                            szm.put("val", RTUHandler.rtumap.get(m.get("id").toString()).getRtudi().get( (Integer)szm.get("s_tongdao") - 1 ).get("va"));
                        }else{
                            szm.put("val", RTUHandler.rtumap.get(m.get("id").toString()).getRtudo().get( (Integer)szm.get("s_tongdao") - 1 ).get("va"));
                        }
                }
                jkdata.put(m.get("id").toString() + "_" + RTUHandler.rtumap.get(m.get("id").toString()).getDatatime(), rtuszlist);
            }
        }
        return jkdata;
    }
    
    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/sendCode"}, method = {POST})
    @ResponseBody
    public String sendCode(String rtuid,String code) {
        NIOServer nserver = new NIOServer();
        try{
            RTUHandler.sendMsg(rtuid, code);
        }catch(Exception e){
            e.printStackTrace();
            return "-1";
        }
        return code;
    }
    
    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/getRemsg"}, method = {POST})
    @ResponseBody
    public String getRemsg() {
        String msg = RTUHandler.remsg;
        RTUHandler.remsg = "";
        return msg;
    }
}
