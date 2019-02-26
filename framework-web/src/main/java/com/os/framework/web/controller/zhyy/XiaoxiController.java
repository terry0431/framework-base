package com.os.framework.web.controller.zhyy;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.service.bundle.zhyy.Message;
import com.os.framework.web.mod.system.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wangbo
 * @date 2016-12-29 12:02:24
 */
@Controller
public class XiaoxiController {

    /**
     * 跳转**查询列表
     *
     * @param request
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/xiaoxi/list", method = GET)
    public String list(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        User user = (User) request.getSession().getAttribute("userInfo");
        String ztflag = request.getParameter("ztflag");
        if (ztflag != null && ztflag.equals("1")) {
            request.setAttribute("ztflag", ztflag);
        }
        Message msg = new Message();
        msg.updateMsgByUser(user);
        return "zhyy/xiaoxi/list";
    }


    /**
     * 跳转**编辑页面
     *
     * @param( id)
     * @param( model)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/xiaoxi/edit", method = GET)
    public String edit(String id, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        request.setAttribute("id", id);
        return "zhyy/xiaoxi/form";
    }

    /**
     * 跳转**查看页面
     *
     * @param( id)
     * @param( model)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/xiaoxi/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "zhyy/xiaoxi/view";
    }

}
