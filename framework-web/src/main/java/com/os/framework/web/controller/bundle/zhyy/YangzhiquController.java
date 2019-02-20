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
public class YangzhiquController {
	/**
	 * 跳转**查询列表
	 * 
	 * @param (request)
	 * @return
	 * @author
	 * @version 1.0
	 */
	@RequestMapping(value = "/con/zhyy/yangzhiqu/list", method = GET)
	public String list(HttpServletRequest request) {
			
		MainDao mainDao = new MainDao();
		List<Map<String,Object>> zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
		return "zhyy/yangzhiqu/list";
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
	@RequestMapping(value = "/con/zhyy/yangzhiqu/add", method = GET)
	public String add(HttpServletRequest request) {
				MainDao mainDao = new MainDao();
				List zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
						return "zhyy/yangzhiqu/form";
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
	@RequestMapping(value = "/con/zhyy/yangzhiqu/edit", method = GET)
	public String edit(String id, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		List zhyy_yangzhichangList = mainDao.queryForList("select * from zhyy_yangzhichang",null);
		request.setAttribute("zhyy_yangzhichangList",zhyy_yangzhichangList);
		request.setAttribute("id",id);
		return "zhyy/yangzhiqu/form";
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
    @RequestMapping(value = "/con/zhyy/yangzhiqu/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "zhyy/yangzhiqu/view";
    }
    
}