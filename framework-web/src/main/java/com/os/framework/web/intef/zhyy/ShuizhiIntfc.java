package com.os.framework.web.intef.zhyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.core.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author @version 1.0
 */
@Controller
public class ShuizhiIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/shuizhi/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_shuizhi.* ";
//        sql += " ,zhyy_yangzhichang.* ";
        sql += " FROM zhyy_shuizhi zhyy_shuizhi ";
//        sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_shuizhi.zhyy_yangzhichang_id = zhyy_yangzhichang.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_shuizhi WHERE 1=1 ";
        
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("id") && !"".equals(map.get("id"))) {
                valuesList.add(map.get("id"));
                sql += " and zhyy_shuizhi.id = ?";
                sqlNum += " and id = ?";
            }
            if (map.containsKey("s_wh_shuiwen") && !"".equals(map.get("s_wh_shuiwen"))) {
                valuesList.add(map.get("s_wh_shuiwen"));
                sql += " and zhyy_shuizhi.s_wh_shuiwen = ?";
                sqlNum += " and s_wh_shuiwen = ?";
            }
            if (map.containsKey("s_wh_yandu") && !"".equals(map.get("s_wh_yandu"))) {
                valuesList.add(map.get("s_wh_yandu"));
                sql += " and zhyy_shuizhi.s_wh_yandu = ?";
                sqlNum += " and s_wh_yandu = ?";
            }
            if (map.containsKey("s_ct_shuiwen") && !"".equals(map.get("s_ct_shuiwen"))) {
                valuesList.add(map.get("s_ct_shuiwen"));
                sql += " and zhyy_shuizhi.s_ct_shuiwen = ?";
                sqlNum += " and s_ct_shuiwen = ?";
            }
            if (map.containsKey("s_ct_yandu") && !"".equals(map.get("s_ct_yandu"))) {
                valuesList.add(map.get("s_ct_yandu"));
                sql += " and zhyy_shuizhi.s_ct_yandu = ?";
                sqlNum += " and s_ct_yandu = ?";
            }
            if (map.containsKey("s_ct_ph") && !"".equals(map.get("s_ct_ph"))) {
                valuesList.add(map.get("s_ct_ph"));
                sql += " and zhyy_shuizhi.s_ct_ph = ?";
                sqlNum += " and s_ct_ph = ?";
            }
            if (map.containsKey("s_ct_d02") && !"".equals(map.get("s_ct_d02"))) {
                valuesList.add(map.get("s_ct_d02"));
                sql += " and zhyy_shuizhi.s_ct_d02 = ?";
                sqlNum += " and s_ct_d02 = ?";
            }
            if (map.containsKey("s_ct_zhuodu") && !"".equals(map.get("s_ct_zhuodu"))) {
                valuesList.add(map.get("s_ct_zhuodu"));
                sql += " and zhyy_shuizhi.s_ct_zhuodu = ?";
                sqlNum += " and s_ct_zhuodu = ?";
            }
            if (map.containsKey("s_ct_shuimian") && !"".equals(map.get("s_ct_shuimian"))) {
                valuesList.add(map.get("s_ct_shuimian"));
                sql += " and zhyy_shuizhi.s_ct_shuimian = ?";
                sqlNum += " and s_ct_shuimian = ?";
            }
            if (map.containsKey("s_ct_shuishen") && !"".equals(map.get("s_ct_shuishen"))) {
                valuesList.add(map.get("s_ct_shuishen"));
                sql += " and zhyy_shuizhi.s_ct_shuishen = ?";
                sqlNum += " and s_ct_shuishen = ?";
            }
            if (map.containsKey("s_year") && !"".equals(map.get("s_year"))) {
                valuesList.add(map.get("s_year"));
                sql += " and zhyy_shuizhi.s_year = ?";
                sqlNum += " and s_year = ?";
            }
            if (map.containsKey("s_month") && !"".equals(map.get("s_month"))) {
                valuesList.add(map.get("s_month"));
                sql += " and zhyy_shuizhi.s_month = ?";
                sqlNum += " and s_month = ?";
            }
            if (map.containsKey("s_day") && !"".equals(map.get("s_day"))) {
                valuesList.add(map.get("s_day"));
                sql += " and zhyy_shuizhi.s_day = ?";
                sqlNum += " and s_day = ?";
            }
            if (map.containsKey("s_date") && !"".equals(map.get("s_date"))) {
                valuesList.add(map.get("s_date"));
                sql += " and zhyy_shuizhi.s_date = ?";
                sqlNum += " and s_date = ?";
            }
            if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id"))) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and zhyy_shuizhi.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
        }

        sql += " order by zhyy_shuizhi.s_date desc";
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

    /**
     * 添加**
     *
     * @param( map)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/shuizhi/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                if (map.get("id") != null) {
                    mainDao.update("zhyy_shuizhi", map);
                } else {
                    mainDao.save("zhyy_shuizhi", map, PKBean.INCREMENTR);
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
    @RequestMapping(value = {"/ifs/zhyy/shuizhi/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, String s_date,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_shuizhi where id = ? or s_date=?", new Object[]{id,s_date});
            map.put("s_date",  DateUtil.convertDateToString((Date)map.get("s_date")) );
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
    @RequestMapping(value = {"/ifs/zhyy/shuizhi/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_shuizhi where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
    
}
