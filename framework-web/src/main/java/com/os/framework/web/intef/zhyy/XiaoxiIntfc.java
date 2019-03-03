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
import com.os.framework.web.service.zhyy.Message;
import com.os.framework.vo.manager.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author @version 1.0
 */
@Controller
public class XiaoxiIntfc {

    /**
     * 获得**列表信息
     *
     * @param arg
     * @param pageNum
     * @param pageSize
     * @param rowTotal
     * @param request
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/list"}, method = {POST})
    @ResponseBody
    public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "";
        String sqlNum = "";
        List<Object> valuesList = new ArrayList<Object>();
        if (user.getType().equals("1")) {//管理账号

            sql = " SELECT t1.*,t2.y_mingcheng,b.id as x_yidu FROM zhyy_xiaoxi t1 "
                    + " left join zhyy_xiaoxi_zhuangtai b on t1.id=b.msg_id and b.user_id= " + user.getId()
                    + " ,zhyy_yangzhichang t2, sys_role_yangzhichang t3, sys_role_user t4 WHERE "
                    + " t1.zhyy_yangzhichang_id = t3.zhyy_yangzhichang_id AND t3.sys_role_id = t4.role_id and t4.user_id=? ";

            sqlNum = " SELECT count(t1.id) as num FROM zhyy_xiaoxi t1, zhyy_yangzhichang t2, sys_role_yangzhichang t3, sys_role_user t4 WHERE "
                    + " t1.zhyy_yangzhichang_id = t3.zhyy_yangzhichang_id AND t3.sys_role_id = t4.role_id and t4.user_id=?  ";
//            msglist = dao.queryForList(sql, new Object[]{m.get("id"), m.get("id")});
            valuesList.add(user.getId());
        } else {//养殖场主账号
            sql = "select t1.*,t2.y_mingcheng,b.id as x_yidu from zhyy_xiaoxi t1 "
                    + " left join zhyy_xiaoxi_zhuangtai b on t1.id=b.msg_id and b.user_id= " + user.getId()
                    + ",zhyy_yangzhichang t2 where t1.zhyy_yangzhichang_id = ? and t2.id=t1.zhyy_yangzhichang_id ";
            sqlNum = "select count(id) as num from zhyy_xiaoxi where zhyy_yangzhichang_id = ? ";
//            msglist = dao.queryForList(sql, new Object[]{m.get("zhyy_yangzhichang_id"), m.get("id")});
            valuesList.add(user.getYzcid());
        }

//        String sql = "SELECT zhyy_xiaoxi.id as mainid , zhyy_xiaoxi.x_systime, zhyy_xiaoxi.x_content, zhyy_xiaoxi.x_jibie  ,zhyy_xiaoxi.x_chuli";
//        sql += " FROM zhyy_xiaoxi zhyy_xiaoxi ";
//        sql += " WHERE 1=1 ";
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }

        if (map.size() != 0) {
            if (map.containsKey("x_chuli") && !"-1".equals(map.get("x_chuli"))) {
                valuesList.add(map.get("x_chuli"));
                sql += " and t1.x_chuli = ?";
                sqlNum += " and x_chuli = ?";
            }
            if (map.containsKey("zhyy_yangzhichang_id") && !"-1".equals(map.get("zhyy_yangzhichang_id"))) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and t1.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
            if (map.containsKey("x_jibie") && !"-1".equals(map.get("x_jibie"))) {
                valuesList.add(map.get("x_jibie"));
                sql += " and t1.x_jibie = ?";
                sqlNum += " and x_jibie = ?";
            }
        }

        sql += " order by t1.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/save"}, method = {POST})
    @ResponseBody
    public String save(String arg,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        User user = (User) request.getSession().getAttribute("userInfo");
        try {
            Map map = null;
            if (arg != null) {
                map = mapper.readValue(arg, Map.class);
                if(map.get("id") != null){
                    map.put("x_chulirenyuan", user.getId());
                    map.put("x_chuli", 1);
                    mainDao.update("zhyy_xiaoxi", map);
                }else{
                    mainDao.save("zhyy_xiaoxi", map, PKBean.NATIVE);
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
     * 更新处理状态
     *
     * @param( map)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/update"}, method = {POST})
    @ResponseBody
    public String update(String id) {
//        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        try {
            String sql = "update zhyy_xiaoxi set x_chuli = 1";
            if (mainDao.execute(sql, null)) {
                return "1";
            } else {
                return "-2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/yidu"}, method = {POST})
    @ResponseBody
    public String yidu(String xxids, HttpServletRequest request) {
//        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Message msg = new Message();
        try {
            mainDao.beginTransaction();
            User user = ((User) request.getSession().getAttribute("userInfo"));
            Message message = new Message();
            Map xxztmap = null;
            if (xxids == null || xxids.equals("")) {
                List<Map<String, Object>> msglist = message.getMsgByUser(user.getId());
                if (msglist != null && msglist.size() > 0) {
                    for (Map m : msglist) {
                        xxztmap = new HashMap();
                        xxztmap.put("user_id", user.getId());
                        xxztmap.put("msg_id", m.get("id"));
                        mainDao.save("zhyy_xiaoxi_zhuangtai", xxztmap, PKBean.NATIVE);
                    }
                }
            } else {
                for (String xxid : xxids.split(",")) {
                    xxztmap = new HashMap();
                    xxztmap.put("user_id", user.getId());
                    xxztmap.put("msg_id", xxid);
                    mainDao.save("zhyy_xiaoxi_zhuangtai", xxztmap, PKBean.NATIVE);
                }
            }
            message.updateMsgByUser(user);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
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
    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForViewMap("select * from zhyy_xiaoxi where id = ?", new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/getByUser"}, method = {POST})
    @ResponseBody
    public List getByUser(HttpServletRequest request) {
        if(request.getSession().getAttribute("userInfo") == null){
            return null;
        }
        String userid = ((User) request.getSession().getAttribute("userInfo")).getId();
        Message message = new Message();
        List msglist = message.getMsgByUser(userid);
        return msglist;
    }

    /**
     * 删除**
     *
     * @param( ids) id的数组
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/xiaoxi/delete"}, method = {POST})
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from zhyy_xiaoxi where id in (" + ids + ")", null);
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }
}
