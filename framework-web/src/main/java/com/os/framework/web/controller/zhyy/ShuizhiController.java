package com.os.framework.web.controller.zhyy;

import com.os.framework.core.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.os.framework.db.dao.MainDao;
import java.util.Date;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * **信息控制类
 *
 * @author
 * @version 1.0
 */
@Controller
public class ShuizhiController {

    /**
     * 跳转**查询列表
     *
     * @param (request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/shuizhi/list", method = GET)
    public String list(HttpServletRequest request) {

        MainDao mainDao = new MainDao();
//		List<Map<String,Object>> zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
//		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
        return "zhyy/shuizhi/list";
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
    @RequestMapping(value = "/con/zhyy/shuizhi/add", method = GET)
    public String add(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//				List zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
//		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
        String nowdate = DateUtil.convertDateToString(new Date());
        request.setAttribute("nowdata", nowdate);
        return "zhyy/shuizhi/form";
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
    @RequestMapping(value = "/con/zhyy/shuizhi/edit", method = GET)
    public String edit(String id, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//		List zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
//		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
        request.setAttribute("id", id);
        return "zhyy/shuizhi/form";
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
    @RequestMapping(value = "/con/zhyy/shuizhi/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "zhyy/shuizhi/view";
    }
    
    
}
