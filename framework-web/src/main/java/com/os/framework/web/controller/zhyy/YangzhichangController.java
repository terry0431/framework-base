package com.os.framework.web.controller.zhyy;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.os.framework.db.dao.MainDao;
import com.os.framework.vo.manager.User;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
/**
 * **信息控制类
 * 
 * @author
 * @version 1.0
 */
@Controller
public class YangzhichangController {
	/**
	 * 跳转**查询列表
	 * 
	 * @param (request)
	 * @return
	 * @author
	 * @version 1.0
	 */
	@RequestMapping(value = "/con/zhyy/yangzhichang/list", method = GET)
	public String list(HttpServletRequest request) {
			
		MainDao mainDao = new MainDao();
		return "zhyy/yangzhichang/list";
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
	@RequestMapping(value = "/con/zhyy/yangzhichang/add", method = GET)
	public String add(HttpServletRequest request) {
				MainDao mainDao = new MainDao();
						return "zhyy/yangzhichang/form";
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
	@RequestMapping(value = "/con/zhyy/yangzhichang/edit", method = GET)
	public String edit(String id, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		request.setAttribute("id",id);
		return "zhyy/yangzhichang/form";
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
    @RequestMapping(value = "/con/zhyy/yangzhichang/view", method = GET)
    public String view(String userId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        if(user == null){
             return "relogin";
        }
        request.setAttribute("userId", userId);
        return "zhyy/yangzhichang/view";
    }
    
    @RequestMapping(value = "/con/zhyy/yangzhichang/viewyzc", method = GET)
    public String viewYzc(String yzcid, HttpServletRequest request) {
        MainDao mainDao = new MainDao();
//        User user = (User) request.getSession().getAttribute("userInfo");
//        if(user == null){
//             return "relogin";
//        }
        Map map = mainDao.queryForMap("select * from zhyy_yangzhichang where id = ?", new Object[]{yzcid});
        request.setAttribute("yzcid", yzcid);
        request.setAttribute("ymap", map);
        return "zhyy/yangzhichang/viewyzc";
    }
}