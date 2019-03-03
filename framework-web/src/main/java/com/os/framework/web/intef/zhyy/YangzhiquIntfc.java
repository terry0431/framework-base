package com.os.framework.web.intef.zhyy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import com.os.framework.web.service.zhyy.CheckRTUData;
import com.os.framework.web.socket.NIOServer;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author @version 1.0
 */
@Controller
public class YangzhiquIntfc {

    /**
     * 获得**列表信息
     *
     * @return
     * @param( map)
     * @param( pageNum)
     * @param( pageSize)
     * @param( rowTotal)
     * @param( request)
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_yangzhiqu.id as mainid , zhyy_yangzhiqu.y_num, zhyy_yangzhiqu.y_x, zhyy_yangzhiqu.y_y, zhyy_yangzhiqu.y_bianhao, zhyy_yangzhiqu.y_shuishen  ";
        sql += " ,zhyy_yangzhichang.* ";
        sql += " FROM zhyy_yangzhiqu zhyy_yangzhiqu ";
        sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_yangzhiqu.zhyy_yangzhichang_id = zhyy_yangzhichang.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_yangzhiqu WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("y_num") && !"".equals(map.get("y_num"))) {
                valuesList.add(map.get("y_num"));
                sql += " and zhyy_yangzhiqu.y_num = ?";
                sqlNum += " and y_num = ?";
            }
            if (map.containsKey("y_x") && !"".equals(map.get("y_x"))) {
                valuesList.add(map.get("y_x"));
                sql += " and zhyy_yangzhiqu.y_x = ?";
                sqlNum += " and y_x = ?";
            }
            if (map.containsKey("y_y") && !"".equals(map.get("y_y"))) {
                valuesList.add(map.get("y_y"));
                sql += " and zhyy_yangzhiqu.y_y = ?";
                sqlNum += " and y_y = ?";
            }
            if (map.containsKey("y_bianhao") && !"".equals(map.get("y_bianhao"))) {
                valuesList.add(map.get("y_bianhao"));
                sql += " and zhyy_yangzhiqu.y_bianhao = ?";
                sqlNum += " and y_bianhao = ?";
            }
            if (map.containsKey("y_shuishen") && !"".equals(map.get("y_shuishen"))) {
                valuesList.add(map.get("y_shuishen"));
                sql += " and zhyy_yangzhiqu.y_shuishen = ?";
                sqlNum += " and y_shuishen = ?";
            }
           
            if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id"))) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and zhyy_yangzhiqu.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
        }

        sql += " order by zhyy_yangzhiqu.id desc";
        Map mapNum = mainDao.queryForMap(sqlNum, valuesList.toArray());
        List list = mainDao.queryForList(sql, valuesList.toArray(), pageNum, pageSize);
        if (rowTotal == -1) {
            rowTotal = Integer.parseInt(mapNum.get("num").toString());
        }
        Map returnMap = new HashMap();
        returnMap.put("dataList", list);
        returnMap.put("rowTotal", rowTotal);
        return returnMap;
    }

    /**
     * 添加**
     *
     * @param( map)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                if (map.get("id") != null) {
                    mainDao.update("zhyy_yangzhiqu", map);
                } else {
                    mainDao.save("zhyy_yangzhiqu", map, PKBean.INCREMENTR);
                }
                return "1";
            } else {
                return "-2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_yangzhiqu where id = ?", new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 删除**
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_yangzhiqu where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }

    /**
     * 获取养殖区当前状态
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/getYzqZhuangtai"}, method = {POST})
    @ResponseBody
    public Map<String, Boolean[]> getYzqZhuangtai(String yzcid, HttpServletRequest request) {
        Map<String, Boolean[]> yzqbj = new HashMap();
        Map<String,HashMap<String,Object>> logmap ;
        try {
            Map<String ,Map<String,Map<String,Object> > > bjlog = CheckRTUData.bjlog;
            for(String key : bjlog.keySet()){
                yzqbj.put(key, new Boolean[]{false,false});
                for(String logkey : bjlog.get(key).keySet() ){
                    if(bjlog.get(key).get(logkey) != null ){
                        if(bjlog.get(key).get(logkey).get("x_leibie").toString().equals("1") ){//设备 故障
                            yzqbj.get(key)[1] =true;
                        }else{// 数值异常
                            yzqbj.get(key)[0] =true;
                        }
                    }
                }
            }
            return yzqbj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取养殖区当前详细状态
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/getYzqXiangxi"}, method = {POST})
    @ResponseBody
    public Map<String,Object> getYzqXiangxi(String yzcid, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        Map<String,Map<String,Map<String, Object>>> revalue = new HashMap();
        Map<String,Object> rvalue = new HashMap();
        List keylist = new ArrayList();
        Map<String,Map<String, Object>> szobj ;
        try {
            
            String sql = "select * from zhyy_yangzhiqu where zhyy_yangzhichang_id = ? order by y_num";
            List<Map<String, Object>> yzqlist = mainDao.queryForList(sql, new Object[]{yzcid});
            for(Map yzqmap : yzqlist){
                keylist.add(yzqmap.get("id")+ "_" + yzqmap.get("y_bianhao"));
                sql = "select * from zhyy_rtu_shezhi where zhyy_yangzhiqu_id = ? order by s_tongdao";
                List<Map<String, Object>> szlist = mainDao.queryForList(sql, new Object[]{yzqmap.get("id")});
                RTUMod rtumod ;
                Map msgmap = null; 
                Map<String,Map<String,Object> > yzqbjmap = CheckRTUData.bjlog.get(yzqmap.get("id").toString());
                
                szobj = new HashMap();
                for(Map<String,Object> m : szlist){
                    
                    rtumod = NIOServer.rtumap.get(m.get("zhyy_rtu_id"));
                    if(rtumod == null){
                        continue;
                    }
                    m.put("datatime", rtumod.getDatatime());
                    if(yzqbjmap != null){
                        msgmap = yzqbjmap.get(m.get("zhyy_rtu_id") + "_" + m.get("s_leibie") + "_" + m.get("s_tongdao"));
                    }else{
                        msgmap = null;
                    }
                    if(msgmap != null){
                        m.put("style", "red");
                        m.put("title", msgmap.get("x_content"));
                    }else{
                        m.put("style", "green");
                        m.put("title", "");
                    }
                    if(m.get("s_leibie").toString().equals("ai")){
                        m.put("val", rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                    }
                    else if(m.get("s_leibie").toString().equals("di")){
                        m.put("val", rtumod.getRtudi().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                    }else if(m.get("s_leibie").toString().equals("do")){
                        m.put("val", rtumod.getRtudo().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                    }
                    szobj.put(m.get("s_attr").toString(), m);
                }
                revalue.put(yzqmap.get("id")+ "_" + yzqmap.get("y_bianhao"), szobj);
            }
            rvalue.put("keylist", keylist);
            rvalue.put("datamap", revalue);
            return rvalue;
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return null;
        }
    }
    @RequestMapping(value = {"/ifs/zhyy/yangzhiqu/getYzq"}, method = {POST})
    @ResponseBody
    public List<Map<String, Object>> getYzq(String yzqid, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        try {
            
            String sql = "select * from zhyy_rtu_shezhi where zhyy_yangzhiqu_id = ? order by s_tongdao";
            List<Map<String, Object>> szlist = mainDao.queryForList(sql, new Object[]{yzqid});
            RTUMod rtumod ;
            Map msgmap = null; 
            Map<String,Map<String,Object> > yzqbjmap = CheckRTUData.bjlog.get(yzqid);
            for(Map<String,Object> m : szlist){
                rtumod = NIOServer.rtumap.get(m.get("zhyy_rtu_id"));
                if(rtumod == null){
                    continue;
                }
                m.put("datatime", rtumod.getDatatime());
                if(yzqbjmap != null){
                    msgmap = yzqbjmap.get(m.get("zhyy_rtu_id") + "_" + m.get("s_leibie") + "_" + m.get("s_tongdao"));
                }else{
                    msgmap = null;
                }
                if(msgmap != null){
                    m.put("style", "red");
                    m.put("title", msgmap.get("x_content"));
                }else{
                    m.put("style", "green");
                    m.put("title", "");
                }
                if(m.get("s_leibie").toString().equals("ai")){
                    m.put("val", rtumod.getRtuai().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                }
                else if(m.get("s_leibie").toString().equals("di")){
                    m.put("val", rtumod.getRtudi().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                }else if(m.get("s_leibie").toString().equals("do")){
                    m.put("val", rtumod.getRtudo().get( (Integer)m.get("s_tongdao") - 1 ).get("va")) ;
                }
            }
            return szlist;
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return null;
        }
    }
}