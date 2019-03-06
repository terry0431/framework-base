package com.os.framework.web.intef.zhyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.os.framework.web.handler.zhyy.RTUHandler;
import org.springframework.stereotype.Controller;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.DateUtil;
import com.os.framework.web.cache.zhyy.SystemCache;
import com.os.framework.web.socket.NIOServer;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author @version 1.0
 */
@Controller
public class RtuIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/rtu/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_rtu.id as mainid   ";
        sql += " ,zhyy_yangzhichang.* ";
        sql += " FROM zhyy_rtu zhyy_rtu ";
        sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_rtu.zhyy_yangzhichang_id = zhyy_yangzhichang.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_rtu WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id"))) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and zhyy_rtu.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
        }

        sql += " order by zhyy_rtu.id desc";
        Map mapNum = mainDao.queryForMap(sqlNum, valuesList.toArray());
        List<Map<String,Object>> list = mainDao.queryForList(sql, valuesList.toArray(), pageNum, pageSize);
        Date sd = null;
        Date cd = null;
        for (Map m : list) {
            if (RTUHandler.rtumap.get(m.get("mainid")) != null) {
                m.put("servertime", DateUtil.convertDatetimeToString(RTUHandler.rtumap.get(m.get("mainid")).getServertime()));
                m.put("datetime", RTUHandler.rtumap.get(m.get("mainid")).getDatatime());
                sd = RTUHandler.rtumap.get(m.get("mainid")).getServertime();
                cd = DateUtil.convertStringToDatetime(RTUHandler.rtumap.get(m.get("mainid")).getDatatime());
                m.put("sjc", ((sd.getTime() - cd.getTime()) / 1000 ) + "秒");
            } else {
                m.put("servertime", "");
                m.put("datetime", "");
                m.put("sjc", "");
            }
        }

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
    @RequestMapping(value = {"/ifs/zhyy/rtu/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                mainDao.save("zhyy_rtu", map, PKBean.ASSIGNED);
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
    @RequestMapping(value = {"/ifs/zhyy/rtu/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_rtu where id = ?", new Object[]{id});
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
    @RequestMapping(value = {"/ifs/zhyy/rtu/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_rtu where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
    @RequestMapping(value = {"/ifs/zhyy/shebeijiankong/setTiem"}, method = {POST})
    @ResponseBody
    public String setTiem(String rtuid) {
        NIOServer nserver = new NIOServer();
        try{
            Date d = new Date();
            String code = "#SETTIME," + DateUtil.convertDatetimeToString(d) + ";";
            RTUHandler.sendMsg(rtuid, code);
        }catch(Exception e){
            e.printStackTrace();
            return "-1";
        }
        return "1";
    }
    
    /**
     * 删除**
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/rtu/initCache"}, method = {POST})
    @ResponseBody
    public String initCache(HttpServletRequest request) {
        SystemCache sysCache = new SystemCache();
        sysCache.initCatch();
        return "1";
    }
}
