/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.web.intef.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
public class RoleIntfc {
    /**
     * 获得角色列表信息
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
    @RequestMapping(value = {"/ifs/role/list"}, method = {POST})
    @ResponseBody
    public Map getRoleList(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
    	MainDao mainDao = new MainDao();
        String sql = "SELECT id,r_name AS NAME,r_description AS description, r_type AS type FROM sys_role WHERE r_flag=1";
        String sqlNum = "select count(id) as num from sys_role WHERE r_flag=1";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if (arg != null) {
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (Exception ex) {

            }
        }
        if (map.size() != 0) {
            if (map.containsKey("name") && !"".equals(map.get("name"))) {
                valuesList.add(map.get("name"));
                sql += " and r_name like ?";
                sqlNum+= " and r_name like ?";
            }
            if (map.containsKey("type") && !"".equals(map.get("type"))) {
                valuesList.add(map.get("type"));
                sql += " and r_type = ?";
                sqlNum+= " and r_type = ?";
            }
        }
        sql += " order by id";
        Map mapNum = mainDao.queryForMap(sqlNum, valuesList.toArray());
        List list = mainDao.queryForList(sql, valuesList.toArray(), pageNum, pageSize);
        String str = "";
        for(int i=0,len=list.size(); i<len; i++){
        	str = ((Map)list.get(i)).get("type").toString();
        	if("1".equals(str)){
        		((Map)list.get(i)).put("type", "菜单角色");
        		((Map)list.get(i)).put("setmodule", "<a href=\"javascript:setmodules("+((Map)list.get(i)).get("id")+");\"><img style=\"cursor: pointer\" src=\"common/style1/images/icon_8.gif\"></a>");
        		((Map)list.get(i)).put("setlanmu", "");
        	}else if("2".equals(str)){
        		((Map)list.get(i)).put("type", "养殖场角色");
        		((Map)list.get(i)).put("setmodule", "");
        		((Map)list.get(i)).put("setlanmu", "<a href=\"javascript:setlanmus("+((Map)list.get(i)).get("id")+");\"><img style=\"cursor: pointer\" src=\"common/style1/images/icon_8.gif\"></a>");
        	}
        }
        if (rowTotal == -1) {
            long a = Long.parseLong(mapNum.get("num").toString()) ;
            rowTotal = (int) a;
        }
        Map returnMap = new HashMap();
        returnMap.put("dataList", list);
        returnMap.put("rowTotal", rowTotal);
        return returnMap;
    }
    
    /**
     * 添加角色
     *
     * @return
     * @param( map) 角色信息Map集合
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/save"}, method = {POST})
    @ResponseBody
    public String save(HashMap map, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        try {
        	map.put("r_flag", "1");
            mainDao.save("sys_role", map, PKBean.INCREMENTR);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    /**
     * 编辑角色
     *
     * @return
     * @param( map) 角色信息Map集合
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/update"}, method = {POST})
    @ResponseBody
    public String update(HashMap map, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        try {
            //if(mainDao.update("gf001", map ," where id=?",new Object[]{map.get("id")} )  ){
            if (mainDao.update("sys_role", map, "where id=?", new Object[]{map.get("id")})) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 删除角色
     *
     * @return
     * @param( ids) id的数组
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/delete"}, method = {POST})
    @ResponseBody
    public String remove(String ids[], HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        mainDao.beginTransaction();
        try {
            for (int i = 0; i < ids.length; i++) {
                boolean flag = mainDao.execute("delete from sys_role where id =?", new Object[]{ids[i]});
                if (!flag) {
                    return "0";
                }
               if(!mainDao.execute("delete from sys_role_user where role_id=?",new Object[]{ids[i]})){
                   return "0";
               }
               if(!mainDao.execute("delete from sys_role_menu where role_id=?",new Object[]{ids[i]})){
                   return "0";
               }
            }
            mainDao.commit();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            return "0";
        }
    }

    /**
     * 保存用户角色
     *
     * @return
     * @param( list) 用户的集合
     * @param( roleId) 角色ID
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/saveUserAuthority"}, method = {POST})
    @ResponseBody
    public int saveUserAuthority(List<Map<String, String>> list, String roleId, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        int returnVal = 0;
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from sys_role_user where role_id= " + roleId, null);
            for (Map map : list) {
                mainDao.save("sys_role_user", map, PKBean.INCREMENTR);
            }
            mainDao.commit();
            returnVal = 1;
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            returnVal = 0;
        }
        return returnVal;
    }

    /**
     * 保存模块角色
     *
     * @param savedata
     * @param roleId
     * @param request
     * @return
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/saveModuleAuthority"}, method = {POST})
    @ResponseBody
    public int saveModuleAuthority(String savedata, String roleId, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
    	MainDao mainDao = new MainDao();
        int returnVal = 0;
        mainDao.beginTransaction();
        try {
            List<Map<String,String>> list = null;
            if (savedata != null) { 
                list = mapper.readValue(savedata, List.class);
            }
            mainDao.execute("delete from sys_role_menu where role_id= " + roleId, null);
            for (Map map : list) {
                mainDao.save("sys_role_menu", map, PKBean.INCREMENTR);
            }
            mainDao.commit();
            returnVal = 1;
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            returnVal = 0;
        }
        return returnVal;
    }
    
    /**
     * 保存养殖场角色
     *
     * @return
     * @param( list) 模块的集合
     * @param( roleId) 角色ID
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/role/saveYzcAuthority"}, method = {POST})
    @ResponseBody
    public int saveYzcAuthority(List<Map<String, String>> list, String roleId,String zhandianId, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        int returnVal = 0;
        mainDao.beginTransaction();
        try {
            mainDao.execute("delete from cms_role_lanmu where sys_role_id= " + roleId + " and cms_zhandian_id = " + zhandianId, null);
            for (Map map : list) {
                mainDao.save("cms_role_lanmu", map, PKBean.INCREMENTR);
            }
            mainDao.commit();
            returnVal = 1;
        } catch (Exception e) {
            e.printStackTrace();
            mainDao.rollback();
            returnVal = 0;
        }
        return returnVal;
    }
}
