package com.os.framework.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 模块管理类
 *
 * @author wj
 * @version 1.0.1
 */
@Controller
public class MenuController {

    /**
     * 用于获得所有的模块信息
     *
     * @param model   模型
     * @param request 请求
     * @return String
     * @author wangj
     * @version 1.0.1
     */
    @RequestMapping(value = "/con/menu/list", method = GET)
    public String getMenuList(ModelMap model, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        String treeSql = "SELECT * FROM sys_menu WHERE m_enable = 1 ORDER BY m_num";
        List<Map<String, Object>> menuList = mainDao.queryForList(treeSql, null);
        model.addAttribute("treeStr", this.getListString(menuList));
        return "menu/menulist";
    }

    /**
     * 根据传入的数据信息列表，生成json数组，传到页面生成动态列表
     *
     * @param list 模块信息列表
     * @return String
     * @author wangj
     * @version 1.0.2
     */
    public String getListString(List<Map<String, Object>> list) {
    	MainDao mainDao = new MainDao();
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 1;
        for (Map map : list) {
            Map counts = mainDao.queryForMap("select count(id) as num from  sys_menu where m_parent_id =?", new Object[]{map.get("id")});
            int num = Integer.parseInt(counts.get("num").toString());
            if ("0".equals(map.get("m_parent_id").toString())) {
                if (num > 0) {
                    stringBuffer.append("{id:" + map.get("id") + ",order:" + i + ",isLeaf:false,dataObject:{m_name:'" + map.get("m_name") +
                            "',m_num:'" + map.get("m_num") + "'},userObject:{isGroup:true}},");
                } else {
                    stringBuffer.append("{id:" + map.get("id") + ",order:" + i + ",isLeaf:false,dataObject:{m_name:'" + map.get("m_name") +
                            "',m_num:'" + map.get("m_num") + "'},userObject:{isGroup:true},isLoad:true},");
                }
                i++;
            } else {
                if (num > 0) {
                    stringBuffer.append("{id:" + map.get("id") + ",pid:" + map.get("m_parent_id") + ",isLeaf:false,dataObject:{m_name:'" + map.get("m_name") +
                            "',m_num:'" + map.get("m_num") + "'},userObject:{isGroup:true}},");
                } else {
                    stringBuffer.append("{id:" + map.get("id") + ",pid:" + map.get("m_parent_id") + ",isLeaf:false,dataObject:{m_name:'" + map.get("m_name") +
                            "',m_num:'" + map.get("m_num") + "'},userObject:{isGroup:true},isLoad:true},");
                }
            }
        }
        String trees = (stringBuffer.substring(0, stringBuffer.toString().length() - 1)) + "]";
        return trees;

    }

    /**
     * 进入新增模块信息页面
     *
     * @param parentId 父节点id
     * @param model    模型
     * @param request  请求
     * @return String
     * @author wangj
     * @version 1.0.1
     */
    @RequestMapping("/menu/toAddMenu.do")
    public String toAddMenu(String parentId, ModelMap model, HttpServletRequest request) {
        model.addAttribute("parentId", parentId);
        return "menu/menuadd";
    }

    /**
     * 新增加模块节点
     *
     * @param map 新增节点信息，包括节点名称，节点父节点id
     * @param parentId 父节点id
     * @return int
     * @author wangj
     * @version 1.0.1
     */
    public String saveMenu(HashMap map, String parentId, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        try {
            int id = mainDao.save("sys_menu", map, PKBean.INCREMENTR);
            map.put("id", id);
            if (parentId.equals("0")) {
                mainDao.execute("update sys_menu set m_path ='" + id + "',m_level='1' where id =" + id, null);
                return id + "";
            } else {
                List<Map<String, Object>> list = mainDao.queryForList("select m_path,m_level from sys_menu where id =" + parentId, null);

                map.put("m_level", Integer.parseInt(list.get(0).get("m_level").toString()) + 1);
                map.put("m_path", list.get(0).get("m_path") + "-" + id);
                mainDao.execute("update sys_menu set m_path ='" + map.get("m_path") + "',m_level='" + map.get("m_level") + "' where id =" + id, null);
            }
            return id + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 进入编辑模块信息页面
     *
     * @param menuId  节点id
     * @param model   模型
     * @param request 请求
     * @return String
     * @author wangj
     * @version 1.0.2
     */
    @RequestMapping("/menu/toEditMenu.do")
    public String toEditMenu(String menuId, ModelMap model, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        Map map = mainDao.queryForMap("select * from sys_menu where id = ?", new Object[]{menuId});
        model.addAttribute("map", map);
        model.addAttribute("menuId", menuId);
        return "menu/menuedit";
    }

    /**
     * 编辑模块信息
     *
     * @param map 修改模块信息的内容
     * @return String
     * @author wangj
     * @version 1.0.1
     */
    public String updateMenu(Map map, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        try {
            if (mainDao.update("sys_menu", map, " where id = ?", new Object[]{map.get("id")})) {
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
     * 删除节点
     *
     * @param id 删除节点的id
     * @return String
     * @author wangj
     * @version 1.0.3
     */
    public String removeMenu(String id, HttpServletRequest request) {
    	MainDao mainDao = new MainDao();
        try {
            boolean flag = mainDao.execute("delete from sys_menu where id=?", new Object[]{id});
            if (!flag) {
                return "0";
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
