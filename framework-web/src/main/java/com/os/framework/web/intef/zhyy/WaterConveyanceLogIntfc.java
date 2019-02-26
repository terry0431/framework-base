package com.os.framework.web.intef.zhyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
/**
 * 
 * @author
 * @version 1.0
 */
@Controller
public class WaterConveyanceLogIntfc {

	/**
	 * 获得**列表信息
	 * 
	 * @return
	 * @param( map)
	 * @param( pageNum)
	 * @param( pageSize)
	 * @param( rowTotal)
	 * @param( request)
	 * @author
	 * @version 1.0
	 */
	@RequestMapping(value = {"/ifs/econtorl/log/list"}, method = {POST})
	@ResponseBody
	public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		String sql = "SELECT con_log.id as mainid , con_log.sys_time, con_log.c_chitang, con_log.c_shijian  ";
		sql += " FROM con_log con_log ";
		sql += " WHERE 1=1 ";
		String sqlNum = "SELECT count(id) as num FROM con_log WHERE 1=1 ";
		List<Object> valuesList = new ArrayList<Object>();
		Map map = null;
	        if(arg != null){
	            try {
	                map = mapper.readValue(arg, Map.class);
	            } catch (Exception ex) {
	                
	            }
	        }

		if (map.size() != 0) {
			if (map.containsKey("sys_time") && !"".equals(map.get("sys_time"))) {
				valuesList.add(map.get("sys_time"));
				sql += " and con_log.sys_time = ?";
				sqlNum += " and sys_time = ?";
			}
			if (map.containsKey("c_chitang") && !"".equals(map.get("c_chitang"))) {
				valuesList.add(map.get("c_chitang"));
				sql += " and con_log.c_chitang = ?";
				sqlNum += " and c_chitang = ?";
			}
			if (map.containsKey("c_shijian") && !"".equals(map.get("c_shijian"))) {
				valuesList.add(map.get("c_shijian"));
				sql += " and con_log.c_shijian = ?";
				sqlNum += " and c_shijian = ?";
			}
		}

		sql += " order by con_log.id desc";
		Map mapNum = mainDao.queryForMap(sqlNum, valuesList.toArray());
		List list = mainDao.queryForViewList(sql, valuesList.toArray(), pageNum, pageSize);
		if (rowTotal == -1) {
			rowTotal = Integer.parseInt(mapNum.get("num").toString());
		}
		Map returnMap = new HashMap();
		returnMap.put("dataList", list);
		returnMap.put("rowTotal", rowTotal);
		return returnMap;
	}


	/**
	 * 添加**
	 * 
	 * @param( map)
	 * @param( request)
	 * @return
	 * @author
	 * @version 1.0
	 */
    @RequestMapping(value = {"/ifs/econtorl/log/save"}, method = {POST})
    @ResponseBody
	public String save(String arg, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		try {
			Map map = null;
		        if(arg != null){
			map = mapper.readValue(arg, Map.class);
			if(map.get("id") != null){
	                    mainDao.update("con_log", map);
	                }else{
	                    mainDao.save("con_log", map, PKBean.INCREMENTR);
	                }
			return "1";
		        }else{
				return "-2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @author
	 * @version 1.0
	 */
    @RequestMapping(value = {"/ifs/econtorl/log/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from con_log where id = ?", new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

	/**
	 * 删除**
	 * 
	 * @param( ids) id的数组
	 * @return
	 * @author
	 * @version 1.0
	 */
    @RequestMapping(value = {"/ifs/econtorl/log/delete"}, method = {POST})
    @ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		mainDao.beginTransaction();
		try {
			mainDao.execute("delete from con_log where id in ("+ids+")", null);
			mainDao.commit();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			mainDao.rollback();
			return "0";
		}
	}
}