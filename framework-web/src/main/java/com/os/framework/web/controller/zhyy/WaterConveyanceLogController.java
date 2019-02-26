package com.os.framework.web.controller.zhyy;


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
public class WaterConveyanceLogController {
	/**
	 * 跳转**查询列表
	 * 
	 * @param (request)
	 * @return
	 * @author
	 * @version 1.0
	 */
	@RequestMapping(value = "/con/econtorl/log/list", method = GET)
	public String list(HttpServletRequest request) {
			
		MainDao mainDao = new MainDao();
		return "econtorl/log/list";
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
	@RequestMapping(value = "/con/econtorl/log/add", method = GET)
	public String add(HttpServletRequest request) {
				MainDao mainDao = new MainDao();
						return "econtorl/log/form";
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
	@RequestMapping(value = "/con/econtorl/log/edit", method = GET)
	public String edit(String id, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		request.setAttribute("id",id);
		return "econtorl/log/form";
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
    @RequestMapping(value = "/con/econtorl/log/view", method = GET)
    public String view(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "zhyy/qixiang/view";
    }

}