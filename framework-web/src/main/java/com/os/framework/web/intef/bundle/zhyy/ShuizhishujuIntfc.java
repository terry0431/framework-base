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
import com.os.framework.core.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @author
 * @version 1.0
 */
@Controller
public class ShuizhishujuIntfc {

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
	@RequestMapping(value = {"/ifs/zhyy/shuizhishuju/list"}, method = {POST})
	@ResponseBody
	public Map list(String arg, int pageNum, int pageSize, int rowTotal, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		String sql = "SELECT zhyy_shuizhishuju.id as mainid , zhyy_shuizhishuju.s_shuiwen, zhyy_shuizhishuju.s_yandu, zhyy_shuizhishuju.s_ph, zhyy_shuizhishuju.s_d02, zhyy_shuizhishuju.s_zhuodu, zhyy_shuizhishuju.s_shuimian, zhyy_shuizhishuju.s_shuishen, zhyy_shuizhishuju.s_year, zhyy_shuizhishuju.s_month, zhyy_shuizhishuju.s_day, zhyy_shuizhishuju.s_systime, zhyy_shuizhishuju.s_cengshu, zhyy_shuizhishuju.s_caijishijian, zhyy_shuizhishuju.s_flag ,zhyy_shuizhishuju.s_hour ";
		sql += " ,zhyy_yangzhichang.* ";
		sql += " ,zhyy_yangzhiqu.* ";
		sql += " ,zhyy_rtu.* ";
		sql += " FROM zhyy_shuizhishuju zhyy_shuizhishuju ";
		sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_shuizhishuju.zhyy_yangzhichang_id = zhyy_yangzhichang.id "; 
		sql += " left join zhyy_yangzhiqu zhyy_yangzhiqu on zhyy_shuizhishuju.zhyy_yangzhiqu_id = zhyy_yangzhiqu.id "; 
		sql += " left join zhyy_rtu zhyy_rtu on zhyy_shuizhishuju.zhyy_rtu_id = zhyy_rtu.id "; 
		sql += " WHERE 1=1 ";
		String sqlNum = "SELECT count(id) as num FROM zhyy_shuizhishuju WHERE 1=1 ";
		List<Object> valuesList = new ArrayList<Object>();
		Map map = null;
	        if(arg != null){
	            try {
	                map = mapper.readValue(arg, Map.class);
	            } catch (Exception ex) {
	                
	            }
	        }

		if (map.size() != 0) {
			if (map.containsKey("s_shuiwen") && !"".equals(map.get("s_shuiwen"))) {
				valuesList.add(map.get("s_shuiwen"));
				sql += " and zhyy_shuizhishuju.s_shuiwen = ?";
				sqlNum += " and s_shuiwen = ?";
			}
			if (map.containsKey("s_yandu") && !"".equals(map.get("s_yandu"))) {
				valuesList.add(map.get("s_yandu"));
				sql += " and zhyy_shuizhishuju.s_yandu = ?";
				sqlNum += " and s_yandu = ?";
			}
			if (map.containsKey("s_ph") && !"".equals(map.get("s_ph"))) {
				valuesList.add(map.get("s_ph"));
				sql += " and zhyy_shuizhishuju.s_ph = ?";
				sqlNum += " and s_ph = ?";
			}
			if (map.containsKey("s_d02") && !"".equals(map.get("s_d02"))) {
				valuesList.add(map.get("s_d02"));
				sql += " and zhyy_shuizhishuju.s_d02 = ?";
				sqlNum += " and s_d02 = ?";
			}
			if (map.containsKey("s_zhuodu") && !"".equals(map.get("s_zhuodu"))) {
				valuesList.add(map.get("s_zhuodu"));
				sql += " and zhyy_shuizhishuju.s_zhuodu = ?";
				sqlNum += " and s_zhuodu = ?";
			}
			if (map.containsKey("s_shuimian") && !"".equals(map.get("s_shuimian"))) {
				valuesList.add(map.get("s_shuimian"));
				sql += " and zhyy_shuizhishuju.s_shuimian = ?";
				sqlNum += " and s_shuimian = ?";
			}
			if (map.containsKey("s_shuishen") && !"".equals(map.get("s_shuishen"))) {
				valuesList.add(map.get("s_shuishen"));
				sql += " and zhyy_shuizhishuju.s_shuishen = ?";
				sqlNum += " and s_shuishen = ?";
			}
			if (map.containsKey("s_year") && !"".equals(map.get("s_year"))) {
				valuesList.add(map.get("s_year"));
				sql += " and zhyy_shuizhishuju.s_year = ?";
				sqlNum += " and s_year = ?";
			}
			if (map.containsKey("s_month") && !"".equals(map.get("s_month"))) {
				valuesList.add(map.get("s_month"));
				sql += " and zhyy_shuizhishuju.s_month = ?";
				sqlNum += " and s_month = ?";
			}
			if (map.containsKey("s_day") && !"".equals(map.get("s_day"))) {
				valuesList.add(map.get("s_day"));
				sql += " and zhyy_shuizhishuju.s_day = ?";
				sqlNum += " and s_day = ?";
			}
			if (map.containsKey("s_systime") && !"".equals(map.get("s_systime"))) {
				valuesList.add(map.get("s_systime"));
				sql += " and zhyy_shuizhishuju.s_systime = ?";
				sqlNum += " and s_systime = ?";
			}
			if (map.containsKey("s_cengshu") && !"".equals(map.get("s_cengshu"))) {
				valuesList.add(map.get("s_cengshu"));
				sql += " and zhyy_shuizhishuju.s_cengshu = ?";
				sqlNum += " and s_cengshu = ?";
			}
			if (map.containsKey("s_caijishijian") && !"".equals(map.get("s_caijishijian"))) {
				valuesList.add(map.get("s_caijishijian"));
				sql += " and zhyy_shuizhishuju.s_caijishijian = ?";
				sqlNum += " and s_caijishijian = ?";
			}
			if (map.containsKey("s_flag") && !"".equals(map.get("s_flag"))) {
				valuesList.add(map.get("s_flag"));
				sql += " and zhyy_shuizhishuju.s_flag = ?";
				sqlNum += " and s_flag = ?";
			}
			if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id"))) {
				valuesList.add(map.get("zhyy_yangzhichang_id"));
				sql += " and zhyy_shuizhishuju.zhyy_yangzhichang_id = ?";
				sqlNum += " and zhyy_yangzhichang_id = ?";
			}
			if (map.containsKey("zhyy_yangzhiqu_id") && !"".equals(map.get("zhyy_yangzhiqu_id"))) {
				valuesList.add(map.get("zhyy_yangzhiqu_id"));
				sql += " and zhyy_shuizhishuju.zhyy_yangzhiqu_id = ?";
				sqlNum += " and zhyy_yangzhiqu_id = ?";
			}
			if (map.containsKey("zhyy_rtu_id") && !"".equals(map.get("zhyy_rtu_id"))) {
				valuesList.add(map.get("zhyy_rtu_id"));
				sql += " and zhyy_shuizhishuju.zhyy_rtu_id = ?";
				sqlNum += " and zhyy_rtu_id = ?";
			}
		}

		sql += " order by zhyy_shuizhishuju.id desc";
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
    @RequestMapping(value = {"/ifs/zhyy/shuizhishuju/save"}, method = {POST})
    @ResponseBody
	public String save(String arg, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		MainDao mainDao = new MainDao();
		try {
			Map map = null;
		        if(arg != null){
			map = mapper.readValue(arg, Map.class);
			if(map.get("id") != null){
	                    mainDao.update("zhyy_shuizhishuju", map);
	                }else{
	                    mainDao.save("zhyy_shuizhishuju", map, PKBean.INCREMENTR);
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
    @RequestMapping(value = {"/ifs/zhyy/shuizhishuju/get"}, method = {POST})
    @ResponseBody
    public Map get(String id, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = null;
        try {
            map = mainDao.queryForMap("select * from zhyy_shuizhishuju where id = ?", new Object[]{id});
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
    @RequestMapping(value = {"/ifs/zhyy/shuizhishuju/delete"}, method = {POST})
    @ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		MainDao mainDao = new MainDao();
		mainDao.beginTransaction();
		try {
			mainDao.execute("delete from zhyy_shuizhishuju where id in ("+ids+")", null);
			mainDao.commit();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			mainDao.rollback();
			return "0";
		}
	}

    /**
     * 获取多层水质数据 eg 温度 盐度 do2等
     * @param rtuid
     * @param yzqid
     * @param riqi
     * @param request
     * @return
     */
    @RequestMapping(value = {"/ifs/zhyy/shuizhishuju/getnowlist"}, method = {POST})
    @ResponseBody
    public Map getnowlist(String rtuid,String yzqid,String riqi,String rqflag,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = new HashMap();
        List<List<Map<String,Object>>> datalist = new ArrayList(); 
        String sql = "";
        List<Map<String,Object>> list = null;
        try {
            sql = "select * from zhyy_rtu_shezhi where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_obj='zhyy_shuizhishuju'";
            List<Map<String,Object>> setlist = mainDao.queryForList(sql,new Object[]{rtuid,yzqid});
            
            Calendar calendar = Calendar.getInstance();
            if(rqflag != null && !rqflag.equals("")){
                Date bdate = null;
                Date edate = null;
                String briqi = "";
                String eriqi = "";
                if(rqflag.equals("1")){ //本周
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    edate = calendar.getTime();
                    int weeknum = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    calendar.add(Calendar.DAY_OF_MONTH, 0 - weeknum);
                    bdate = calendar.getTime();
                }else if(rqflag.equals("2")){//上周
                    int weeknum = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    calendar.add(Calendar.DAY_OF_MONTH, (0 - weeknum) - 1);
                    edate = calendar.getTime();
                    calendar.add(Calendar.DAY_OF_MONTH, -6);
                    bdate = calendar.getTime();
                }else if(rqflag.equals("3")){//本月
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    edate = calendar.getTime();
//                    int monthnum = calendar.get(Calendar.DAY_OF_MONTH) ;
//                    calendar.add(Calendar.DAY_OF_MONTH, 1 - monthnum );
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
                    bdate = calendar.getTime();
                }else if(rqflag.equals("4")){//上月
                    int monthnum = calendar.get(Calendar.DAY_OF_MONTH) ;
                    calendar.add(Calendar.DAY_OF_MONTH, (0 - monthnum) - 1 );
                    edate = calendar.getTime();
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
                    bdate = calendar.getTime();
                }
                briqi = DateUtil.convertDateToString(bdate);
                eriqi = DateUtil.convertDateToString(edate);
                sql = "select * from zhyy_shuizhishuju where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_caijishijian >= ? and s_caijishijian <= ? and s_flag =1 order by s_cengshu,id";

                list = mainDao.queryForList(sql , new Object[]{rtuid,yzqid,briqi,eriqi });
                
            }else{
                if(riqi != null && !riqi.equals("")){
                    Date date = DateUtil.convertStringToDate(riqi);
                    calendar.setTime(date);
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
    //            int day = 20;
                //sql = "select * from zhyy_shuizhishuju where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_flag=1 and s_year=? and s_month=? and s_day = ? and s_cengshu > 0 order by s_cengshu,id";
                //暂时去掉di过滤条件
                sql = "select * from zhyy_shuizhishuju where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_year=? and s_month=? and s_day = ? and s_flag =1 order by s_cengshu,id";
                
    //            sql = "select * from zhyy_shuizhi where  zhyy_shebei_id=? and s_year=? and s_month=? and s_day = ?  and s_ct_cengshu = ?";
                list = mainDao.queryForList(sql , new Object[]{rtuid,yzqid,year,month,day });
            }
            
            
            String cengshu = "";
            List tmplist = new ArrayList();
            
            int cs = 1;
            for(Map m : list ){
                if(!cengshu.equals("") && !cengshu.equals(m.get("s_cengshu").toString())){
//                   map.put("list" + cs ,tmplist);
                    cengshu = m.get("s_cengshu").toString();
                    datalist.add(tmplist);
                    tmplist = new ArrayList();
                    cs ++;
                }
                if(cengshu.equals("")){
                    cengshu = m.get("s_cengshu").toString();
                }
                tmplist.add(m);
            }
            if(tmplist != null && tmplist.size() > 0){
                datalist.add(tmplist);
            }
            map.put("setlsit", setlist);
            map.put("datalsit", datalist);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取单层水质数据  eg 水深、电流等
     * @param rtuid
     * @param yzqid
     * @param riqi
     * @param request
     * @return
     */
    @RequestMapping(value = {"/ifs/zhyy/shuizhishuju/getdcnowlist"}, method = {POST})
    @ResponseBody
    public Map getdcnowlist(String rtuid,String yzqid,String riqi ,String rqflag,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        Map map = new HashMap();
        List<List<Map<String,Object>>> datalist = new ArrayList(); 
        String sql = "";
        try {
            Calendar calendar = Calendar.getInstance();
            if(riqi != null && !riqi.equals("")){
                Date date = DateUtil.convertStringToDate(riqi);
                calendar.setTime(date);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int day = 20;
            sql = "select * from zhyy_rtu_shezhi where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? ";
            List<Map<String,Object>> setlist = mainDao.queryForList(sql,new Object[]{rtuid,yzqid});
            List<Map<String,Object>> list = null;
            if(rqflag == null || rqflag.equals("")){
                //sql = "select * from zhyy_shuizhishuju where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_flag=1 and s_year=? and s_month=? and s_day = ? s_cengshu > 0 order by id";
                //暂时去掉di过滤条件
                sql = "select  s_shuishen,s_dianliu_1,s_dianliu_2,s_caijishijian from zhyy_shuizhishuju_10fz where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and  s_year=? and s_month=? and s_day = ?  order by id";
    //            sql = "select * from zhyy_shuizhi where  zhyy_shebei_id=? and s_year=? and s_month=? and s_day = ?  and s_ct_cengshu = ?";
                list = mainDao.queryForList(sql , new Object[]{rtuid,yzqid,year,month,day });

            }else{
                Date bdate = null;
                Date edate = null;
                String briqi = "";
                String eriqi = "";
                if(rqflag.equals("1")){ //本周
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    edate = calendar.getTime();
                    int weeknum = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    calendar.add(Calendar.DAY_OF_MONTH, 0 - weeknum);
                    bdate = calendar.getTime();
                }else if(rqflag.equals("2")){//上周
                    int weeknum = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    calendar.add(Calendar.DAY_OF_MONTH, (0 - weeknum) - 1);
                    edate = calendar.getTime();
                    calendar.add(Calendar.DAY_OF_MONTH, -6);
                    bdate = calendar.getTime();
                }else if(rqflag.equals("3")){//本月
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    edate = calendar.getTime();
//                    int monthnum = calendar.get(Calendar.DAY_OF_MONTH) ;
//                    calendar.add(Calendar.DAY_OF_MONTH, 0 - monthnum );、
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
                    bdate = calendar.getTime();
                }else if(rqflag.equals("4")){//上月
                    int monthnum = calendar.get(Calendar.DAY_OF_MONTH) ;
                    calendar.add(Calendar.DAY_OF_MONTH, (0 - monthnum) - 1 );
                    edate = calendar.getTime();
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
                    bdate = calendar.getTime();
                }
                briqi = DateUtil.convertDateToString(bdate);
                eriqi = DateUtil.convertDateToString(edate);
                sql = "select  s_shuishen,s_dianliu_1,s_dianliu_2,s_caijishijian from zhyy_shuizhishuju_10fz where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_caijishijian >= ? and s_caijishijian <= ? order by id";
                
    //            sql = "select * from zhyy_shuizhi where  zhyy_shebei_id=? and s_year=? and s_month=? and s_day = ?  and s_ct_cengshu = ?";
                list = mainDao.queryForList(sql , new Object[]{rtuid,yzqid,briqi,eriqi });
                 
//                sql = "select * from zhyy_shuizhishuju where zhyy_rtu_id=? and zhyy_yangzhiqu_id=? and s_caijishijian >= ? and s_caijishijian <= edate and s_flag =1 order by s_cengshu,id";
//
//                list = mainDao.queryForList(sql , new Object[]{rtuid,yzqid,bdate,edate });
            }
            
//            String cengshu = "";
//            List tmplist = new ArrayList();
            
//            int cs = 1;
//            for(Map m : list ){
//                if(!cengshu.equals("") && !cengshu.equals(m.get("s_cengshu").toString())){
////                   map.put("list" + cs ,tmplist);
//                    cengshu = m.get("s_cengshu").toString();
//                    datalist.add(tmplist);
//                    tmplist = new ArrayList();
//                    cs ++;
//                }
//                if(cengshu.equals("")){
//                    cengshu = m.get("s_cengshu").toString();
//                }
//                tmplist.add(m);
//            }
//            if(tmplist != null && tmplist.size() > 0){
//                datalist.add(tmplist);
//            }
            while(list.size() > 1200){
                //int n = list.size() / 1500;
                for(int i = 0;i < list.size();i ++){
                    if(i > 0 && i % 2 == 0){
                        list.remove(i);
                    }
                }
            }
            map.put("setlsit", setlist);
            map.put("datalsit", list);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}