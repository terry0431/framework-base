package com.os.framework.web.intef.zhyy;

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
public class YangzhichangIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/yangzhichang/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_yangzhichang.id as mainid , zhyy_yangzhichang.y_mingcheng, zhyy_yangzhichang.y_lianxiren, zhyy_yangzhichang.y_lianxidianhua, zhyy_yangzhichang.zuobiao_x, zhyy_yangzhichang.zuobiao_y, zhyy_yangzhichang.y_dizhi, zhyy_yangzhichang.y_sheng, zhyy_yangzhichang.y_shi, zhyy_yangzhichang.y_quxian  ";
        sql += " FROM zhyy_yangzhichang zhyy_yangzhichang ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_yangzhichang WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("y_mingcheng") && !"".equals(map.get("y_mingcheng"))) {
                valuesList.add(map.get("y_mingcheng"));
                sql += " and zhyy_yangzhichang.y_mingcheng = ?";
                sqlNum += " and y_mingcheng = ?";
            }
            if (map.containsKey("y_lianxiren") && !"".equals(map.get("y_lianxiren"))) {
                valuesList.add(map.get("y_lianxiren"));
                sql += " and zhyy_yangzhichang.y_lianxiren = ?";
                sqlNum += " and y_lianxiren = ?";
            }
            if (map.containsKey("y_lianxidianhua") && !"".equals(map.get("y_lianxidianhua"))) {
                valuesList.add(map.get("y_lianxidianhua"));
                sql += " and zhyy_yangzhichang.y_lianxidianhua = ?";
                sqlNum += " and y_lianxidianhua = ?";
            }
            if (map.containsKey("zuobiao_x") && !"".equals(map.get("zuobiao_x"))) {
                valuesList.add(map.get("zuobiao_x"));
                sql += " and zhyy_yangzhichang.zuobiao_x = ?";
                sqlNum += " and zuobiao_x = ?";
            }
            if (map.containsKey("zuobiao_y") && !"".equals(map.get("zuobiao_y"))) {
                valuesList.add(map.get("zuobiao_y"));
                sql += " and zhyy_yangzhichang.zuobiao_y = ?";
                sqlNum += " and zuobiao_y = ?";
            }
            if (map.containsKey("y_dizhi") && !"".equals(map.get("y_dizhi"))) {
                valuesList.add(map.get("y_dizhi"));
                sql += " and zhyy_yangzhichang.y_dizhi = ?";
                sqlNum += " and y_dizhi = ?";
            }
            if (map.containsKey("y_sheng") && !"".equals(map.get("y_sheng"))) {
                valuesList.add(map.get("y_sheng"));
                sql += " and zhyy_yangzhichang.y_sheng = ?";
                sqlNum += " and y_sheng = ?";
            }
            if (map.containsKey("y_shi") && !"".equals(map.get("y_shi"))) {
                valuesList.add(map.get("y_shi"));
                sql += " and zhyy_yangzhichang.y_shi = ?";
                sqlNum += " and y_shi = ?";
            }
            if (map.containsKey("y_quxian") && !"".equals(map.get("y_quxian"))) {
                valuesList.add(map.get("y_quxian"));
                sql += " and zhyy_yangzhichang.y_quxian = ?";
                sqlNum += " and y_quxian = ?";
            }
        }

        sql += " order by zhyy_yangzhichang.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/yangzhichang/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                mainDao.saveOrUpdate("zhyy_yangzhichang", map, PKBean.ASSIGNED);

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
    @RequestMapping(value = {"/ifs/zhyy/yangzhichang/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_yangzhichang where id = ?", new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    
}
