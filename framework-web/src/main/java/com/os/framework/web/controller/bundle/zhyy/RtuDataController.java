package com.os.framework.web.controller.bundle.zhyy;

import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.os.framework.db.dao.MainDao;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * **信息控制类
 *
 * @author
 * @version 1.0
 */
@Controller
public class RtuDataController {

    /**
     * 跳转**查询列表
     *
     * @param (request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/rtu_data/list", method = GET)
    public String list(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        List<Map<String, Object>> zhyy_rtuList = mainDao.queryForList("select * from zhyy_rtu", null);
        request.setAttribute("zhyy_rtuList", zhyy_rtuList);
        return "zhyy/rtu_data/list";
    }

    /**
     * 跳转到**添加页面
     *
     * @param( model)
     * @param( request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/rtu_data/add", method = GET)
    public String add(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        List zhyy_rtuList = mainDao.queryForList("select * from zhyy_rtu", null);
        request.setAttribute("zhyy_rtuList", zhyy_rtuList);
        return "zhyy/rtu_data/form";
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
    @RequestMapping(value = "/con/zhyy/rtu_data/edit", method = GET)
    public String edit(String id, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        List zhyy_rtuList = mainDao.queryForList("select * from zhyy_rtu", null);
        request.setAttribute("zhyy_rtuList", zhyy_rtuList);
        request.setAttribute("id", id);
        return "zhyy/rtu_data/form";
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
    @RequestMapping(value = "/con/zhyy/rtu_data/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "zhyy/qixiang/view";
    }

}
