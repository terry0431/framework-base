package com.os.framework.web.controller.bundle.zhyy;

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
public class ZhibiaoController {

    /**
     * 跳转**查询列表
     *
     * @param (request)
     * @return
     * @author
     * @version 1.0
     */
    @RequestMapping(value = "/con/zhyy/zhibiao/list", method = GET)
    public String list(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//        List<Map<String, Object>> zhyy_shebeiList = mainDao.queryForList("select * from zhyy_shebei", null);
//        request.setAttribute("zhyy_shebeiList", zhyy_shebeiList);
        return "zhyy/zhibiao/list";
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
    @RequestMapping(value = "/con/zhyy/zhibiao/add", method = GET)
    public String add(HttpServletRequest request) {
        MainDao mainDao = new MainDao();
        String nowdate = DateUtil.convertDateToString(new Date());
//        List zhyy_shebeiList = mainDao.queryForList("select * from zhyy_shebei", null);
//        request.setAttribute("zhyy_shebeiList", zhyy_shebeiList);
        request.setAttribute("nowdata", nowdate);
        return "zhyy/zhibiao/form";
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
    @RequestMapping(value = "/con/zhyy/zhibiao/edit", method = GET)
    public String edit(String id, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//        List zhyy_shebeiList = mainDao.queryForList("select * from zhyy_shebei", null);
//        request.setAttribute("zhyy_shebeiList", zhyy_shebeiList);
        request.setAttribute("id", id);
        return "zhyy/zhibiao/form";
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
    @RequestMapping(value = "/con/zhyy/zhibiao/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//        List zhyy_shebeiList = mainDao.queryForList("select * from zhyy_shebei", null);
//        request.setAttribute("zhyy_shebeiList", zhyy_shebeiList);
        request.setAttribute("id", id);
        return "zhyy/zhibiao/view";
    }

}
