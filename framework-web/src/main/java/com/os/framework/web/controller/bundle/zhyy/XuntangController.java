package com.os.framework.web.controller.bundle.zhyy;

import com.os.framework.core.util.DateUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.bean.PermissionBean;
import com.os.framework.web.sys.mod.User;
import java.util.Date;
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
public class XuntangController {
    
    @RequestMapping(value = "/con/zhyy/xuntang/list", method = GET)
    public String xuntangList(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//	List<Map<String,Object>> t_equipment_pondList = mainDao.queryForList("select * from t_equipment_pond",null);
//        request.setAttribute("t_equipment_pondList",t_equipment_pondList);
	return "zhyy/xuntang/list";
    }
    
    @RequestMapping(value = "/con/zhyy/xuntang/add", method = GET)
    public String addXuntang(HttpServletRequest request) {
        String cjriqi = "";
        if(request.getParameter("cjriqi") != null){
            cjriqi = request.getParameter("cjriqi");
        }else{
            cjriqi = DateUtil.convertDateToString(new Date());
        }
        request.setAttribute("cjriqi", cjriqi);
	return "zhyy/xuntang/form";
    }
    
    @RequestMapping(value = "/con/zhyy/xuntang/viewlist", method = GET)
    public String xuntangViewList(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
	return "zhyy/xuntang/viewlist";
    }
    
    @RequestMapping(value = "/con/zhyy/xuntang/view", method = GET)
    public String view(HttpServletRequest request) {
        String cjriqi = "";
        if(request.getParameter("cjriqi") != null){
            cjriqi = request.getParameter("cjriqi");
        }else{
            cjriqi = DateUtil.convertDateToString(new Date());
        }
        request.setAttribute("cjriqi", cjriqi);
	return "zhyy/xuntang/view";
    }
}