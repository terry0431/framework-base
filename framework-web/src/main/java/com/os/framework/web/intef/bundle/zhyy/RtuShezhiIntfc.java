package com.os.framework.web.intef.bundle.zhyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author @version 1.0
 */
@Controller
public class RtuShezhiIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/rtu_shezhi/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_rtu_shezhi.id as mainid , zhyy_rtu_shezhi.s_tongdao, zhyy_rtu_shezhi.s_leibie, zhyy_rtu_shezhi.s_mingcheng, zhyy_rtu_shezhi.s_attr, zhyy_rtu_shezhi.s_obj, zhyy_rtu_shezhi.s_danwei  ";
        sql += " ,zhyy_yangzhiqu.* ";
        sql += " ,zhyy_rtu.* ";
        sql += " FROM zhyy_rtu_shezhi zhyy_rtu_shezhi ";
        sql += " left join zhyy_yangzhiqu zhyy_yangzhiqu on zhyy_rtu_shezhi.zhyy_yangzhiqu_id = zhyy_yangzhiqu.id ";
        sql += " left join zhyy_rtu zhyy_rtu on zhyy_rtu_shezhi.zhyy_rtu_id = zhyy_rtu.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_rtu_shezhi WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("s_tongdao") && !"".equals(map.get("s_tongdao"))) {
                valuesList.add(map.get("s_tongdao"));
                sql += " and zhyy_rtu_shezhi.s_tongdao = ?";
                sqlNum += " and s_tongdao = ?";
            }
            if (map.containsKey("s_leibie") && !"".equals(map.get("s_leibie"))) {
                valuesList.add(map.get("s_leibie"));
                sql += " and zhyy_rtu_shezhi.s_leibie = ?";
                sqlNum += " and s_leibie = ?";
            }
            if (map.containsKey("s_mingcheng") && !"".equals(map.get("s_mingcheng"))) {
                valuesList.add(map.get("s_mingcheng"));
                sql += " and zhyy_rtu_shezhi.s_mingcheng = ?";
                sqlNum += " and s_mingcheng = ?";
            }
            if (map.containsKey("s_attr") && !"".equals(map.get("s_attr"))) {
                valuesList.add(map.get("s_attr"));
                sql += " and zhyy_rtu_shezhi.s_attr = ?";
                sqlNum += " and s_attr = ?";
            }
            if (map.containsKey("s_obj") && !"".equals(map.get("s_obj"))) {
                valuesList.add(map.get("s_obj"));
                sql += " and zhyy_rtu_shezhi.s_obj = ?";
                sqlNum += " and s_obj = ?";
            }
            if (map.containsKey("s_danwei") && !"".equals(map.get("s_danwei"))) {
                valuesList.add(map.get("s_danwei"));
                sql += " and zhyy_rtu_shezhi.s_danwei = ?";
                sqlNum += " and s_danwei = ?";
            }
            if (map.containsKey("zhyy_yangzhiqu_id") && !"".equals(map.get("zhyy_yangzhiqu_id"))) {
                valuesList.add(map.get("zhyy_yangzhiqu_id"));
                sql += " and zhyy_rtu_shezhi.zhyy_yangzhiqu_id = ?";
                sqlNum += " and zhyy_yangzhiqu_id = ?";
            }
            if (map.containsKey("zhyy_rtu_id") && !"".equals(map.get("zhyy_rtu_id"))) {
                valuesList.add(map.get("zhyy_rtu_id"));
                sql += " and zhyy_rtu_shezhi.zhyy_rtu_id = ?";
                sqlNum += " and zhyy_rtu_id = ?";
            }
        }

        sql += " order by zhyy_rtu_shezhi.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/rtu_shezhi/save"}, method = {POST})
    @ResponseBody
    public String save(String rtuid, String arg1, String arg2,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            mainDao.beginTransaction();
            String sql = "delete from zhyy_rtu_shezhi where zhyy_rtu_id=?";
            mainDao.execute(sql, new Object[]{rtuid});
            List<Map> list = null;
            if (arg1 != null) {
                list = mapper.readValue(arg1, List.class);
                for (Map m : list) {
                    mainDao.save("zhyy_rtu_shezhi", m, PKBean.NATIVE);
                }
                
                if (arg2 != null) {
                    sql = "delete from zhyy_baojingshezhi where zhyy_rtu_id=?";
                    mainDao.execute(sql, new Object[]{rtuid});
                    list = mapper.readValue(arg2, List.class);
                    for (Map m : list) {
                        mainDao.save("zhyy_baojingshezhi", m, PKBean.NATIVE);
                    }
                }
                mainDao.commit();
                return "1";
            } else {
                return "-2";
            }
        } catch (Exception e) {
            mainDao.rollback();
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
    @RequestMapping(value = {"/ifs/zhyy/rtu_shezhi/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map<String,List> remap = new HashMap();
        List szlist = null;
        List bjlist = null;
        try {
            szlist = mainDao.queryForList("select * from zhyy_rtu_shezhi where zhyy_rtu_id=?", new Object[]{id});
            bjlist = mainDao.queryForList("select * from zhyy_baojingshezhi where zhyy_rtu_id=?", new Object[]{id});
            remap.put("szlist", szlist);
            remap.put("bjlist", bjlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remap;
    }

    /**
     * 删除**
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/rtu_shezhi/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_rtu_shezhi where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
}
