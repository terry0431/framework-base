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
public class QixiangIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/qixiang/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_qixiang.id as mainid , zhyy_qixiang.q_qiwen, zhyy_qixiang.q_fengxiang, zhyy_qixiang.q_fengli, zhyy_qixiang.q_tianqi, zhyy_qixiang.q_riqi  ";
        sql += " FROM zhyy_qixiang zhyy_qixiang ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_qixiang WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }
        if (map.size() != 0) {
            if (map.containsKey("q_qiwen") && !"".equals(map.get("q_qiwen"))) {
                valuesList.add(map.get("q_qiwen"));
                sql += " and zhyy_qixiang.q_qiwen = ?";
                sqlNum += " and q_qiwen = ?";
            }
            if (map.containsKey("q_fengxiang") && !"".equals(map.get("q_fengxiang"))) {
                valuesList.add(map.get("q_fengxiang"));
                sql += " and zhyy_qixiang.q_fengxiang = ?";
                sqlNum += " and q_fengxiang = ?";
            }
            if (map.containsKey("q_fengli") && !"".equals(map.get("q_fengli"))) {
                valuesList.add(map.get("q_fengli"));
                sql += " and zhyy_qixiang.q_fengli = ?";
                sqlNum += " and q_fengli = ?";
            }

            if (map.containsKey("q_tianqi") && !"".equals(map.get("q_tianqi"))) {
                valuesList.add(map.get("q_tianqi"));
                sql += " and zhyy_qixiang.q_tianqi = ?";
                sqlNum += " and q_tianqi = ?";
            }
            if (map.containsKey("q_riqi") && !"".equals(map.get("q_riqi"))) {
                valuesList.add(map.get("q_riqi"));
                sql += " and zhyy_qixiang.q_riqi = ?";
                sqlNum += " and q_riqi = ?";
            }
            if (map.containsKey("zhyy_shebei_id") && !"".equals(map.get("zhyy_shebei_id"))) {
                valuesList.add(map.get("zhyy_shebei_id"));
                sql += " and zhyy_qixiang.zhyy_shebei_id = ?";
                sqlNum += " and zhyy_shebei_id = ?";
            }
        }

        sql += " order by zhyy_qixiang.q_riqi desc";
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
     *
     * @param arg
     * @param request
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/qixiang/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                if(map.get("id") != null){
                    mainDao.update("zhyy_qixiang", map);
                }else{
                    mainDao.save("zhyy_qixiang", map, PKBean.NATIVE);
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
    @RequestMapping(value = {"/ifs/zhyy/qixiang/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, String q_riqi,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_qixiang where id = ? or q_riqi = ?", new Object[]{id,q_riqi});
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
    @RequestMapping(value = {"/ifs/zhyy/qixiang/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_qixiang where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
}
