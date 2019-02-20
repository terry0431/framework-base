package com.os.framework.web.intef.bundle.zhyy;

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
public class RtuDataIntfc {

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
	@RequestMapping(value = {"/ifs/zhyy/rtu_data/list"}, method = {POST})
	@ResponseBody
	public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		String sql = "SELECT zhyy_rtu_data.id as mainid , zhyy_rtu_data.r_systime, zhyy_rtu_data.r_caijishijian, zhyy_rtu_data.r_year, zhyy_rtu_data.r_month, zhyy_rtu_data.r_day, zhyy_rtu_data.ai_1, zhyy_rtu_data.ai_2, zhyy_rtu_data.ai_3, zhyy_rtu_data.ai_4, zhyy_rtu_data.ai_5, zhyy_rtu_data.ai_6, zhyy_rtu_data.ai_7, zhyy_rtu_data.ai_8, zhyy_rtu_data.di_1, zhyy_rtu_data.di_2, zhyy_rtu_data.di_3, zhyy_rtu_data.di_4, zhyy_rtu_data.di_5, zhyy_rtu_data.di_6, zhyy_rtu_data.di_7, zhyy_rtu_data.di_8, zhyy_rtu_data.do_1, zhyy_rtu_data.do_2, zhyy_rtu_data.do_3, zhyy_rtu_data.do_4, zhyy_rtu_data.do_5, zhyy_rtu_data.do_6, zhyy_rtu_data.do_7, zhyy_rtu_data.do_8  ";
		sql += " ,zhyy_rtu.* ";
		sql += " FROM zhyy_rtu_data zhyy_rtu_data ";
		sql += " left join zhyy_rtu zhyy_rtu on zhyy_rtu_data.zhyy_rtu_id = zhyy_rtu.id "; 
		sql += " WHERE 1=1 ";
		String sqlNum = "SELECT count(id) as num FROM zhyy_rtu_data WHERE 1=1 ";
		List<Object> valuesList = new ArrayList<Object>();
		Map map = null;
	        if(arg != null){
	            try {
	                map = mapper.readValue(arg, Map.class);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }

		if (map.size() != 0) {
			if (map.containsKey("r_systime") && !"".equals(map.get("r_systime"))) {
				valuesList.add(map.get("r_systime"));
				sql += " and zhyy_rtu_data.r_systime = ?";
				sqlNum += " and r_systime = ?";
			}
			if (map.containsKey("r_caijishijian") && !"".equals(map.get("r_caijishijian"))) {
				valuesList.add(map.get("r_caijishijian"));
				sql += " and zhyy_rtu_data.r_caijishijian = ?";
				sqlNum += " and r_caijishijian = ?";
			}
			if (map.containsKey("r_year") && !"".equals(map.get("r_year"))) {
				valuesList.add(map.get("r_year"));
				sql += " and zhyy_rtu_data.r_year = ?";
				sqlNum += " and r_year = ?";
			}
			if (map.containsKey("r_month") && !"".equals(map.get("r_month"))) {
				valuesList.add(map.get("r_month"));
				sql += " and zhyy_rtu_data.r_month = ?";
				sqlNum += " and r_month = ?";
			}
			if (map.containsKey("r_day") && !"".equals(map.get("r_day"))) {
				valuesList.add(map.get("r_day"));
				sql += " and zhyy_rtu_data.r_day = ?";
				sqlNum += " and r_day = ?";
			}
			if (map.containsKey("ai_1") && !"".equals(map.get("ai_1"))) {
				valuesList.add(map.get("ai_1"));
				sql += " and zhyy_rtu_data.ai_1 = ?";
				sqlNum += " and ai_1 = ?";
			}
			if (map.containsKey("ai_2") && !"".equals(map.get("ai_2"))) {
				valuesList.add(map.get("ai_2"));
				sql += " and zhyy_rtu_data.ai_2 = ?";
				sqlNum += " and ai_2 = ?";
			}
			if (map.containsKey("ai_3") && !"".equals(map.get("ai_3"))) {
				valuesList.add(map.get("ai_3"));
				sql += " and zhyy_rtu_data.ai_3 = ?";
				sqlNum += " and ai_3 = ?";
			}
			if (map.containsKey("ai_4") && !"".equals(map.get("ai_4"))) {
				valuesList.add(map.get("ai_4"));
				sql += " and zhyy_rtu_data.ai_4 = ?";
				sqlNum += " and ai_4 = ?";
			}
			if (map.containsKey("ai_5") && !"".equals(map.get("ai_5"))) {
				valuesList.add(map.get("ai_5"));
				sql += " and zhyy_rtu_data.ai_5 = ?";
				sqlNum += " and ai_5 = ?";
			}
			if (map.containsKey("ai_6") && !"".equals(map.get("ai_6"))) {
				valuesList.add(map.get("ai_6"));
				sql += " and zhyy_rtu_data.ai_6 = ?";
				sqlNum += " and ai_6 = ?";
			}
			if (map.containsKey("ai_7") && !"".equals(map.get("ai_7"))) {
				valuesList.add(map.get("ai_7"));
				sql += " and zhyy_rtu_data.ai_7 = ?";
				sqlNum += " and ai_7 = ?";
			}
			if (map.containsKey("ai_8") && !"".equals(map.get("ai_8"))) {
				valuesList.add(map.get("ai_8"));
				sql += " and zhyy_rtu_data.ai_8 = ?";
				sqlNum += " and ai_8 = ?";
			}
			if (map.containsKey("di_1") && !"".equals(map.get("di_1"))) {
				valuesList.add(map.get("di_1"));
				sql += " and zhyy_rtu_data.di_1 = ?";
				sqlNum += " and di_1 = ?";
			}
			if (map.containsKey("di_2") && !"".equals(map.get("di_2"))) {
				valuesList.add(map.get("di_2"));
				sql += " and zhyy_rtu_data.di_2 = ?";
				sqlNum += " and di_2 = ?";
			}
			if (map.containsKey("di_3") && !"".equals(map.get("di_3"))) {
				valuesList.add(map.get("di_3"));
				sql += " and zhyy_rtu_data.di_3 = ?";
				sqlNum += " and di_3 = ?";
			}
			if (map.containsKey("di_4") && !"".equals(map.get("di_4"))) {
				valuesList.add(map.get("di_4"));
				sql += " and zhyy_rtu_data.di_4 = ?";
				sqlNum += " and di_4 = ?";
			}
			if (map.containsKey("di_5") && !"".equals(map.get("di_5"))) {
				valuesList.add(map.get("di_5"));
				sql += " and zhyy_rtu_data.di_5 = ?";
				sqlNum += " and di_5 = ?";
			}
			if (map.containsKey("di_6") && !"".equals(map.get("di_6"))) {
				valuesList.add(map.get("di_6"));
				sql += " and zhyy_rtu_data.di_6 = ?";
				sqlNum += " and di_6 = ?";
			}
			if (map.containsKey("di_7") && !"".equals(map.get("di_7"))) {
				valuesList.add(map.get("di_7"));
				sql += " and zhyy_rtu_data.di_7 = ?";
				sqlNum += " and di_7 = ?";
			}
			if (map.containsKey("di_8") && !"".equals(map.get("di_8"))) {
				valuesList.add(map.get("di_8"));
				sql += " and zhyy_rtu_data.di_8 = ?";
				sqlNum += " and di_8 = ?";
			}
			if (map.containsKey("do_1") && !"".equals(map.get("do_1"))) {
				valuesList.add(map.get("do_1"));
				sql += " and zhyy_rtu_data.do_1 = ?";
				sqlNum += " and do_1 = ?";
			}
			if (map.containsKey("do_2") && !"".equals(map.get("do_2"))) {
				valuesList.add(map.get("do_2"));
				sql += " and zhyy_rtu_data.do_2 = ?";
				sqlNum += " and do_2 = ?";
			}
			if (map.containsKey("do_3") && !"".equals(map.get("do_3"))) {
				valuesList.add(map.get("do_3"));
				sql += " and zhyy_rtu_data.do_3 = ?";
				sqlNum += " and do_3 = ?";
			}
			if (map.containsKey("do_4") && !"".equals(map.get("do_4"))) {
				valuesList.add(map.get("do_4"));
				sql += " and zhyy_rtu_data.do_4 = ?";
				sqlNum += " and do_4 = ?";
			}
			if (map.containsKey("do_5") && !"".equals(map.get("do_5"))) {
				valuesList.add(map.get("do_5"));
				sql += " and zhyy_rtu_data.do_5 = ?";
				sqlNum += " and do_5 = ?";
			}
			if (map.containsKey("do_6") && !"".equals(map.get("do_6"))) {
				valuesList.add(map.get("do_6"));
				sql += " and zhyy_rtu_data.do_6 = ?";
				sqlNum += " and do_6 = ?";
			}
			if (map.containsKey("do_7") && !"".equals(map.get("do_7"))) {
				valuesList.add(map.get("do_7"));
				sql += " and zhyy_rtu_data.do_7 = ?";
				sqlNum += " and do_7 = ?";
			}
			if (map.containsKey("do_8") && !"".equals(map.get("do_8"))) {
				valuesList.add(map.get("do_8"));
				sql += " and zhyy_rtu_data.do_8 = ?";
				sqlNum += " and do_8 = ?";
			}
			if (map.containsKey("zhyy_rtu_id") && !"".equals(map.get("zhyy_rtu_id"))) {
				valuesList.add(map.get("zhyy_rtu_id"));
				sql += " and zhyy_rtu_data.zhyy_rtu_id = ?";
				sqlNum += " and zhyy_rtu_id = ?";
			}
		}

		sql += " order by zhyy_rtu_data.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/rtu_data/save"}, method = {POST})
    @ResponseBody
	public String save(String arg, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		try {
			Map map = null;
		        if(arg != null){
			map = mapper.readValue(arg, Map.class);
			if(map.get("id") != null){
	                    mainDao.update("zhyy_rtu_data", map);
	                }else{
	                    mainDao.save("zhyy_rtu_data", map, PKBean.INCREMENTR);
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
    @RequestMapping(value = {"/ifs/zhyy/rtu_data/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_rtu_data where id = ?", new Object[]{id});
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
    @RequestMapping(value = {"/ifs/zhyy/rtu_data/delete"}, method = {POST})
    @ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		mainDao.beginTransaction();
		try {
			mainDao.execute("delete from zhyy_rtu_data where id in ("+ids+")", null);
			mainDao.commit();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			mainDao.rollback();
			return "0";
		}
	}
}