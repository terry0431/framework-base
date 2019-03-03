package com.os.framework.web.controller.system;

import com.os.framework.core.secret.DesBean;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.service.manager.PermissionBean;
import com.os.framework.vo.manager.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author wangbo
 * @date 2016-12-29 12:02:24
 */
@Controller
public class loginController {

    @RequestMapping(value = "/login", method = POST)
    public String login(HttpServletRequest request) {
        PermissionBean permissionBean = new PermissionBean();
        MainDao mainDao = new MainDao();
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
//        String validateCode = request.getParameter("validateCode");

        HttpSession session = request.getSession();
        if(request.getSession().getAttribute("userInfo") != null){
            User user = (User)session.getAttribute("userInfo");
//            List stairMenuList = permissionBean.getMenulistByUser( ((User)session.getAttribute("userInfo")).getId(), "1");	//一级菜单
//            request.setAttribute("stairMenuList", stairMenuList);
            //return "main";
//            request.getSession().setAttribute("userInfo", user);
            if (!user.getId().equals("-1")) {
                user.setMenus(permissionBean.getMenulistByUser(user.getId()));//菜单列表
            }
//            session.setAttribute("userInfo", user);
            List stairMenuList = permissionBean.getMenulistByUser(user.getId(), "1");	//一级菜单
            request.setAttribute("stairMenuList", stairMenuList);
            if(user.getType().equals("2")){
                session.setAttribute("userId", DesBean.encrypt(user.getId() ) );
                return "redirect:con/zhyy/yangzhichang/view";
            }
            return "index";
        }
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            return "relogin";
        }
        loginName = loginName.trim();
        password = password.trim();
        Map userMap = mainDao.queryForMap("select id,u_password as password,u_passport as username,u_name as name,org_id as organizationid, u_enable,u_leibie,zhyy_yangzhichang_id from sys_userinfo where u_passport=?", new Object[]{loginName});
        if (userMap == null) {
            request.setAttribute("msg", "用户名不存在，请重新输入！");
            return "relogin";
        }
        if (!password.equals(userMap.get("password").toString())) {
            request.setAttribute("msg", "输入密码错误！");
            return "relogin";
        }
        if (userMap.get("u_enable").toString().equals("0")) {
            request.setAttribute("msg", "该账户已被注销");
            return "relogin";
        }
        //String ip = IpUtil.getIp(request);
        //Map<String, Object> orgMap = mainDao.queryForMap("select group_concat(cast(a.id as char),'') as ids from cg_jigou as a  where a.p_id=?", new Object[]{userMap.get("organizationid")});
        User user = new User();
        user.setId(userMap.get("id").toString());
        user.setName(userMap.get("name").toString());
        user.setUsername(userMap.get("username").toString());
        user.setPassword(userMap.get("password").toString());
        user.setType(userMap.get("u_leibie").toString());
        if(user.getType().equals("2")){
            user.setYzcid(userMap.get("zhyy_yangzhichang_id").toString());
        }
        //普通用户需要存入的session数据
        //user.setIPS(ip);
        user.setOrgid(userMap.get("organizationid").toString());//机构id		

        if (!user.getId().equals("-1")) {
            user.setMenus(permissionBean.getMenulistByUser(user.getId()));//菜单列表
        }
        session.setAttribute("userInfo", user);
        List stairMenuList = permissionBean.getMenulistByUser(user.getId(), "1");	//一级菜单
        request.setAttribute("stairMenuList", stairMenuList);
        if(user.getType().equals("2")){
            session.setAttribute("userId", DesBean.encrypt(user.getId() ) );
            return "redirect:con/zhyy/yangzhichang/view";
        }
        //return "main";
        return "index";
    }
    
    @RequestMapping(value = "/index", method = GET)
    public String index(HttpServletRequest request) {
        PermissionBean permissionBean = new PermissionBean();
        HttpSession session = request.getSession();
        MainDao mainDao = new MainDao();
//        String userId 
//        userId = DesBean.decrypt(userId);
//        Map userMap = mainDao.queryForMap("select id,u_password as password,u_passport as username,u_name as name,org_id as organizationid, u_enable,u_leibie,zhyy_yangzhichang_id from sys_userinfo where id=?", new Object[]{userId});
//        if (userMap == null) {
//            request.setAttribute("msg", "用户名不存在，请重新输入！");
//            return "relogin";
//        }
//        User user = new User();
//        user.setId(userMap.get("id").toString());
//        user.setName(userMap.get("name").toString());
//        user.setUsername(userMap.get("username").toString());
//        user.setPassword(userMap.get("password").toString());
//        user.setType(userMap.get("u_leibie").toString());
//        if(user.getType().equals("2")){
//            user.setYzcid(userMap.get("zhyy_yangzhichang_id").toString());
//        }
//        //普通用户需要存入的session数据
//        //user.setIPS(ip);
//        user.setOrgid(userMap.get("organizationid").toString());//机构id		
//
//        if (!user.getId().equals("-1")) {
//            user.setMenus(permissionBean.getMenulistByUser(user.getId()));//菜单列表
//        }
//        session.setAttribute("userInfo", user);
//        List stairMenuList = permissionBean.getMenulistByUser(user.getId(), "1");	//一级菜单
//        request.setAttribute("stairMenuList", stairMenuList);
//        //return "main";
//        return "index";
        if(request.getSession().getAttribute("userInfo") != null){
            User user = (User)session.getAttribute("userInfo");
//            List stairMenuList = permissionBean.getMenulistByUser( ((User)session.getAttribute("userInfo")).getId(), "1");	//一级菜单
//            request.setAttribute("stairMenuList", stairMenuList);
            //return "main";
//            request.getSession().setAttribute("userInfo", user);
            if (!user.getId().equals("-1")) {
                user.setMenus(permissionBean.getMenulistByUser(user.getId()));//菜单列表
            }
//            session.setAttribute("userInfo", user);
            List stairMenuList = permissionBean.getMenulistByUser(user.getId(), "1");	//一级菜单
            request.setAttribute("stairMenuList", stairMenuList);
            return "index";
        }else{
            request.setAttribute("msg", "身份已失效请重新登录");
            return "relogin";
        }
    }
    @RequestMapping(value = "/main", method = POST)
    public String main(String menuId, HttpServletRequest request) {
        PermissionBean permissionBean = new PermissionBean();
        User user = (User) request.getSession().getAttribute("userInfo");
        if(user == null){
            return "relogin";
        }
        request.setAttribute("secondMenuList", permissionBean.getMenulistByUserAndParent(user.getId(), menuId));
        return "include/secondmenu";
    }

    /**
     * 获得左侧菜单
     *
     * @param parentId 父节点ID
     * @param model 模型
     * @param request 请求
     * @return String
     * @author wangj
     * @version 1.0.2
     */
    @RequestMapping("/toLeftTreePage.do")
    public String toLeftTreePage(String parentId, ModelMap model, HttpServletRequest request) {
        PermissionBean permissionBean = new PermissionBean();
        User user = (User) request.getSession().getAttribute("userInfo");
        List thirdMenuList = permissionBean.getMenulistByUserAndParent(user.getId(), parentId);
        Map fourMap = new HashMap();
        for (int i = 0, j = thirdMenuList.size(); i < j; i++) {
            Map map = (Map) thirdMenuList.get(i);
            //List fourthMenuList = mainDao.queryForList(menuSql, new Object[]{map.get("id")});
            List fourthMenuList = permissionBean.getMenulistByUserAndParent(user.getId(), map.get("id").toString());
            fourMap.put(map.get("id").toString(), fourthMenuList);
        }
        model.addAttribute("thirdMenuList", thirdMenuList);
        model.addAttribute("fourMap", fourMap);
        return "tree";
    }
    
    @RequestMapping(value = "/logout", method = GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "relogin";
    }
}
