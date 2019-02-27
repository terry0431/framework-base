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
import core.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author @version 1.0
 */
@Controller
public class QixiangshujuIntfc {

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
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_qixiangshuju.id as mainid , zhyy_qixiangshuju.q_qiwen, zhyy_qixiangshuju.q_fengxiang,zhyy_qixiangshuju.q_fengxiang_code, zhyy_qixiangshuju.q_fengli, zhyy_qixiangshuju.q_tianqi, zhyy_qixiangshuju.q_systime, zhyy_qixiangshuju.q_hour, zhyy_qixiangshuju.q_shidu, zhyy_qixiangshuju.q_year, zhyy_qixiangshuju.q_month, zhyy_qixiangshuju.q_day, zhyy_qixiangshuju.q_qiya, zhyy_qixiangshuju.q_yuliang  ";
//		sql += " ,zhyy_shebei.* ";
        sql += " FROM zhyy_qixiangshuju zhyy_qixiangshuju ";
//		sql += " left join zhyy_shebei zhyy_shebei on zhyy_qixiangshuju.zhyy_shebei_id = zhyy_shebei.id "; 
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_qixiangshuju WHERE 1=1 ";
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
                sql += " and zhyy_qixiangshuju.q_qiwen = ?";
                sqlNum += " and q_qiwen = ?";
            }
            if (map.containsKey("q_fengxiang") && !"".equals(map.get("q_fengxiang"))) {
                valuesList.add(map.get("q_fengxiang"));
                sql += " and zhyy_qixiangshuju.q_fengxiang = ?";
                sqlNum += " and q_fengxiang = ?";
            }
            if (map.containsKey("q_fengli") && !"".equals(map.get("q_fengli"))) {
                valuesList.add(map.get("q_fengli"));
                sql += " and zhyy_qixiangshuju.q_fengli = ?";
                sqlNum += " and q_fengli = ?";
            }
            if (map.containsKey("q_tianqi") && !"".equals(map.get("q_tianqi"))) {
                valuesList.add(map.get("q_tianqi"));
                sql += " and zhyy_qixiangshuju.q_tianqi = ?";
                sqlNum += " and q_tianqi = ?";
            }
            if (map.containsKey("q_systime") && !"".equals(map.get("q_systime"))) {
                valuesList.add(map.get("q_systime"));
                sql += " and zhyy_qixiangshuju.q_systime = ?";
                sqlNum += " and q_systime = ?";
            }
            if (map.containsKey("q_hour") && !"".equals(map.get("q_hour"))) {
                valuesList.add(map.get("q_hour"));
                sql += " and zhyy_qixiangshuju.q_hour = ?";
                sqlNum += " and q_hour = ?";
            }
            if (map.containsKey("q_shidu") && !"".equals(map.get("q_shidu"))) {
                valuesList.add(map.get("q_shidu"));
                sql += " and zhyy_qixiangshuju.q_shidu = ?";
                sqlNum += " and q_shidu = ?";
            }
            if (map.containsKey("q_year") && !"".equals(map.get("q_year"))) {
                valuesList.add(map.get("q_year"));
                sql += " and zhyy_qixiangshuju.q_year = ?";
                sqlNum += " and q_year = ?";
            }
            if (map.containsKey("q_month") && !"".equals(map.get("q_month"))) {
                valuesList.add(map.get("q_month"));
                sql += " and zhyy_qixiangshuju.q_month = ?";
                sqlNum += " and q_month = ?";
            }
            if (map.containsKey("q_day") && !"".equals(map.get("q_day"))) {
                valuesList.add(map.get("q_day"));
                sql += " and zhyy_qixiangshuju.q_day = ?";
                sqlNum += " and q_day = ?";
            }
            if (map.containsKey("q_qiya") && !"".equals(map.get("q_qiya"))) {
                valuesList.add(map.get("q_qiya"));
                sql += " and zhyy_qixiangshuju.q_qiya = ?";
                sqlNum += " and q_qiya = ?";
            }
            if (map.containsKey("q_yuliang") && !"".equals(map.get("q_yuliang"))) {
                valuesList.add(map.get("q_yuliang"));
                sql += " and zhyy_qixiangshuju.q_yuliang = ?";
                sqlNum += " and q_yuliang = ?";
            }
//			if (map.containsKey("zhyy_shebei_id") && !"".equals(map.get("zhyy_shebei_id"))) {
//				valuesList.add(map.get("zhyy_shebei_id"));
//				sql += " and zhyy_qixiangshuju.zhyy_shebei_id = ?";
//				sqlNum += " and zhyy_shebei_id = ?";
//			}
        }

        sql += " order by zhyy_qixiangshuju.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/save"}, method = {POST})
    @ResponseBody
    public String save(String arg, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                if (map.get("id") != null) {
                    mainDao.update("zhyy_qixiangshuju", map);
                } else {
                    mainDao.save("zhyy_qixiangshuju", map, PKBean.INCREMENTR);
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
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_qixiangshuju where id = ?", new Object[]{id});
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
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_qixiangshuju where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/getnowlist"}, method = {POST})
    @ResponseBody
    public Map getnowlist(String sbid ,String riqi,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        List list = null;
        Map map = new HashMap();
        String sql = "";
        try {
            Calendar calendar = Calendar.getInstance();
            if(riqi != null && !riqi.equals("")){
                Date date = DateUtil.convertStringToDate(riqi);
                calendar.setTime(date);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            sql = "select * from zhyy_qixiangshuju where zhyy_shebei_id = ? and q_year=? and q_month=? and q_day = ? and id % 4 != 0 limit 0,1000";
            list = mainDao.queryForList(sql , new Object[]{sbid,year,month,day});
            map.put("datalist",  list );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    } 
    
    
    
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/getfydata"}, method = {POST})
    @ResponseBody
    public List getFyData(String sbid,String riqi,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        List list = null;

        String sql = "";
        try {
            Calendar calendar = Calendar.getInstance();
            if(riqi != null && !riqi.equals("")){
                Date date = DateUtil.convertStringToDate(riqi);
                calendar.setTime(date);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            sql = "select q_hour,FORMAT(AVG(q_fengli),1) as fengli, FORMAT(AVG(q_fengxiang),1) as fengxiang from zhyy_qixiangshuju where zhyy_shebei_id = ? and q_year = ? and q_month=? and q_day=? group by q_hour ";
            list = mainDao.queryForList(sql , new Object[]{sbid,year,month,day});
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/getmgdata"}, method = {POST})
    @ResponseBody
    public List<Map<String,String>> getMgData(String sbid,String riqi,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        List<Map<String,String>> relist = new ArrayList();
        String sql = "";
        try {
            String[] fxs = {"N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
            Float[] fls = {0f,0.5f,2f,4f,6f,8f,10f};
            Calendar calendar = Calendar.getInstance();
            if(riqi != null && !riqi.equals("")){
                Date date = DateUtil.convertStringToDate(riqi);
                calendar.setTime(date);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            sql = "select count(*) as snum from zhyy_qixiangshuju where zhyy_shebei_id=? and q_year = ? and q_month=? and q_day=? ";
            Map map = mainDao.queryForMap(sql, new Object[]{sbid,year,month,day});
            Map tmpmap = null;
            for(int i = 0;i < fls.length ;i ++){
                if(i < fls.length - 1){
                    sql = "select q_fengxiang_code,format(count(*)/"+map.get("snum")+"*100,2) as val from zhyy_qixiangshuju " +
                        " where zhyy_shebei_id=? and q_fengli > ? and q_fengli <= ? and q_year = ? and q_month=? and q_day=? " +
                        " group by q_fengxiang_code  order by q_fengxiang_code ";
                    list = mainDao.queryForList(sql , new Object[]{sbid,fls[i],fls[i + 1] ,year,month,day});
                }else{
                    sql = "select q_fengxiang_code,format(count(*)/"+map.get("snum")+"*100,2) as val from zhyy_qixiangshuju " +
                        " where zhyy_shebei_id=? and q_fengli > ?  and q_year = ? and q_month=? and q_day=? " +
                        " group by q_fengxiang_code  order by q_fengxiang_code ";
                    list = mainDao.queryForList(sql , new Object[]{sbid,fls[i] ,year,month,day});
                }
                
                tmpmap = new HashMap();
                if(list != null && !list.isEmpty()){
                    for(Map m : list){
                        for(String fx : fxs){
                            if(m.get("q_fengxiang_code").equals(fx)){
                                tmpmap.put(fx, m.get("val"));
                            }
                        }
                    }
                }
                relist.add(tmpmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relist;
        // key 0.5  value ( key N value 0,3 key nne 0.3)
        
    }
    
    @RequestMapping(value = {"/ifs/zhyy/qixiangshuju/getlastdata"}, method = {POST})
    @ResponseBody
    public Map getlastdata(String sbid,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        List list = null;
        Map map = new HashMap();
        String sql = "";
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            sql = "select * from zhyy_qixiangshuju where zhyy_shebei_id = ? and q_year=? and q_month=? and q_day = ? order by id desc LIMIT 0,1";
            map = mainDao.queryForMap(sql , new Object[]{sbid,year,month,day});
            if(map != null && map.get("q_systime") != null){
                Long longtime = ((Date)map.get("q_systime")).getTime();
                map.put("longtime", longtime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
