package com.os.framework.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.os.framework.db.dao.MainDao;

/**
 * 鐢ㄦ埛淇℃伅鎺у埗绫�
 *
 * @author wangfeng
 * @version 1.0.1
 */
@Controller
public class RoleController {

    @RequestMapping("/con/role/list")
    public String roleList(HttpServletRequest request) {
        return "role/list";
    }

    
    /**
     * 璺宠浆瑙掕壊鏂板椤甸潰
     *
     * @return
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping("/con/role/add")
    public String addRole(HttpServletRequest request) {

        return "role/add";
    }

    /**
     * 璺宠浆瑙掕壊缂栬緫椤甸潰
     *
     * @return
     * @param( id) 瑙掕壊ID
     * @param( model) 瑙掕壊ID
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping("/con/role/edit")
    public String editRole(String id, ModelMap model, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        Map role = mainDao.queryForMap("select id,r_name as name,r_description as description, r_type type from sys_role where id=?", new Object[]{id});
        model.addAttribute("role", role);
        return "role/editrole";
    }

    /**
     * 鑾峰緱鐢ㄦ埛鏍�
     *
     * @return
     * @param( id) 瑙掕壊ID
     * @param( model) 瑙掕壊ID
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping("/con/role/userAuthority")
    public String userAuthority(String id, ModelMap model, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        List<Object> valuesList = new ArrayList<Object>();
        List<Map<String, Object>> organizationList = mainDao.queryForList("SELECT id, o_name as j_name, o_parent_id as p_id FROM sys_org order by id",new Object[]{});
        String userListSql = "SELECT a.org_id,a.id,a.u_name,b.id checked FROM sys_userinfo a LEFT JOIN  (SELECT id,user_id,role_id FROM sys_role_user WHERE role_id=?) b ON a.id=b.user_id where a.u_enable=? order by a.id";
        valuesList.add(id);
        valuesList.add(1);
        List<Map<String, Object>> userList = mainDao.queryForList(userListSql, valuesList.toArray());
//         request.setAttribute("roleId",id);
        StringBuffer stringBuffer = new StringBuffer("[");
        for (Map map : organizationList) {
            String parentId = map.get("p_id").toString();
            if (map.get("p_id") == null) {
                parentId = "0";
            }
            stringBuffer.append("{ id:'").append(map.get("id"))
                    .append("',pId:'").append(parentId)
                    .append("',name:'").append(map.get("j_name")).append("',isParent:true},");
        }
        for (Map map : userList) {
            stringBuffer.append("{ id:'u_").append(map.get("id"))
                    .append("',pId:'").append(map.get("org_id"))
                    .append("',name:'").append(map.get("u_name")).append("'");
            if (!"".equals(map.get("checked").toString())) {
                stringBuffer.append(",checked:true");
            }
            stringBuffer.append("},");
        }
        if (stringBuffer.length() > 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append("]");
        model.addAttribute("tree", stringBuffer.toString());
        model.addAttribute("roleId", id);
//        model.addAttribute("role", role);
        return "role/userauthority";
    }

    /**
     * 鑾峰緱妯″潡鏍�
     *
     * @return
     * @param( id) 瑙掕壊ID
     * @param( model)
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping("/con/role/moduleAuthority")
    public String moduleAuthority(String id, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        List<Object> valuesList = new ArrayList<Object>();
        valuesList.add(id);
        String moduleListSql = "SELECT a.m_name,a.id ,a.m_parent_id,b.id checked FROM sys_menu a LEFT JOIN(SELECT id,menu_id,role_id FROM sys_role_menu WHERE role_id=?) b ON a.id=b.menu_id order by a.id";
        List<Map<String, Object>> moduleList = mainDao.queryForList(moduleListSql, valuesList.toArray());
//         request.setAttribute("roleId",id);
        StringBuffer stringBuffer = new StringBuffer("[");
        for (Map map : moduleList) {
            Map count = mainDao.queryForMap("select count(id) as num from sys_menu where m_parent_id =?", new Object[]{map.get("id")});
            String parentId = map.get("m_parent_id").toString();
            if (map.get("m_parent_id") == null) {
                parentId = "0";
            }
            stringBuffer.append("{ id:'").append(map.get("id"))
                    .append("',pId:'").append(parentId)
                    .append("',name:'").append(map.get("m_name")).append("'");
            if (Long.parseLong(count.get("num").toString()) > 0) {
                stringBuffer.append(",isParent:true");
            }
            if (!"".equals(map.get("checked"))) {
                stringBuffer.append(",checked:true");
            }
            stringBuffer.append("},");
        }
        if (stringBuffer.length() > 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append("]");
        request.setAttribute("tree", stringBuffer.toString());
        request.setAttribute("roleId", id);
//        model.addAttribute("role", role);
        return "role/moduleauthority";
    }
    /**
     * 鑾峰緱鍏绘畺鍦�
     *
     * @return
     * @param( id) 瑙掕壊ID
     * @param( model)
     * @param( request)
     * @author 
     * @version 1.0
     */
    @RequestMapping("/con/role/yzcAuthority")
    public String yzcAuthority(String id,  HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
    	String zhandianid = "";
    	List<Map<String,Object>> zdlist = mainDao.queryForList("select * from cms_zhandian", null);
    	
    	if(!StringUtils.isEmpty(request.getParameter("zhandian_id"))){
    		zhandianid = request.getParameter("zhandian_id");
    	}else{
    		if(zdlist != null && zdlist.size() > 0){
    			zhandianid = zdlist.get(0).get("id").toString();
    		}
    	}
    	
        List<Object> valuesList = new ArrayList<Object>();
        valuesList.add(id);
        valuesList.add(zhandianid);
        String moduleListSql = 	"SELECT a.l_mingcheng,a.id ,a.pid,b.id checked FROM cms_lanmu a "+
								" LEFT JOIN(SELECT id,cms_lanmu_id,sys_role_id FROM cms_role_lanmu WHERE sys_role_id=?) b "+
								" ON a.id=b.cms_lanmu_id  where a.cms_zhandian_id=? order by a.id";
        List<Map<String, Object>> moduleList = mainDao.queryForList(moduleListSql, valuesList.toArray());
//         request.setAttribute("roleId",id);
        StringBuffer stringBuffer = new StringBuffer("[");
        for (Map map : moduleList) {
            Map count = mainDao.queryForMap("select count(id) as num from cms_lanmu where pid =?", new Object[]{map.get("id")});
            String parentId = map.get("pid").toString();
            if (map.get("pid") == null) {
                parentId = "0";
            }
            stringBuffer.append("{ id:'").append(map.get("id"))
                    .append("',pId:'").append(parentId)
                    .append("',name:'").append(map.get("l_mingcheng")).append("'");
            if (Long.parseLong(count.get("num").toString()) > 0) {
                stringBuffer.append(",isParent:true");
            }
            if (!"".equals(map.get("checked"))) {
                stringBuffer.append(",checked:true");
            }
            stringBuffer.append("},");
        }
        if (stringBuffer.length() > 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append("]");
        request.setAttribute("tree", stringBuffer.toString());
        request.setAttribute("roleId", id);
        request.setAttribute("zdlist",zdlist);
        request.setAttribute("zhandianid",zhandianid);
//        model.addAttribute("role", role);
        return "role/yzcauthority";
    }
}
