package com.os.framework.web.controller.bundle.zhyy;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.bean.PermissionBean;
import com.os.framework.web.sys.mod.User;
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
public class ShebeiJiankongController {
    
    @RequestMapping(value = "/con/zhyy/shebeijiankong/nowlist", method = GET)
    public String nowlist(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        String id = "1";
        if(request.getParameter("id") != null){
            id = request.getParameter("id");
        }
        request.setAttribute("", id);
        if(id.equals("1")){
            return "zhyy/shebeijiankong/nowlist";
        }else{
            return "zhyy/shebeijiankong/nowlist_ycz";
        }
	
    }
    
    @RequestMapping(value = "/con/zhyy/shebeijiankong/jklist", method = GET)
    public String jklist(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        String sql = "select * from zhyy_yangzhichang order by id";
        List list = mainDao.queryForList(sql, null);
        request.setAttribute("yzclist", list);
	return "zhyy/shebeijiankong/jklist";
    }
    
    @RequestMapping(value = "/con/zhyy/shebeijiankong/list", method = GET)
    public String list(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
	return "zhyy/shebeijiankong/list";
    }
    
    @RequestMapping(value = "/con/zhyy/shebeijiankong/showlog", method = GET)
    public String showlog(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
	return "zhyy/shebeijiankong/showlog";
    }
    
    @RequestMapping(value = "/con/zhyy/shebeijiankong/console", method = GET)
    public String console(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
	return "zhyy/shebeijiankong/console";
    }
}
