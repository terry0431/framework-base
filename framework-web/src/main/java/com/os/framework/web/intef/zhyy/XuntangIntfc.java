package com.os.framework.web.intef.zhyy;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.util.DateUtil;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class XuntangIntfc {
    Logger log = Logger.getLogger(this.getClass().getName());
    public XuntangIntfc() {
    }

    /**
     * 添加寻塘记录
     * @param caijiriqi 采集日期
     * @param yzlist    养殖对象数据列表
     * @param sblist    设备异常数据列表
     * @param zhlist    灾害异常数据列表
     * @return
     */
    @RequestMapping(value = {"/ifs/zhyy/xuntang/add"}, method = {POST})
    @ResponseBody
    public String Add(String caijiriqi, String yzlist, String sblist, String zhlist) {
        MainDao dao = new MainDao();
        ObjectMapper mapper = new ObjectMapper();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.convertStringToDate(caijiriqi));
        Date nowtime = new Date();
        try {
            //json转换
            List<Map<String, String>> yz_list = null;
            List<Map<String, String>> sb_list = null;
            List<Map<String, String>> zh_list = null;
            if (yzlist != null) {
                yz_list = mapper.readValue(yzlist, List.class);
            }
            if (sblist != null) {
                sb_list = mapper.readValue(sblist, List.class);
            }
            if (zhlist != null) {
                zh_list = mapper.readValue(zhlist, List.class);
            }
            //检查该日记是否已存在记录
            String sql = "select * from zhyy_xuntang where x_cjriqi = ?";
            Map map = dao.queryForMap(sql, new Object[]{caijiriqi});
            if (map == null) {
                map = new HashMap();
                map.put("x_year", calendar.get(Calendar.YEAR));
                map.put("x_month", calendar.get(Calendar.MONTH) - 1);
                map.put("x_day", calendar.get(Calendar.DAY_OF_MONTH));
                map.put("x_cjriqi", caijiriqi);
                map.put("sys_userinfo_id", -1);
                map.put("zhyy_yangzhichang_id", 1);
            }
            if (sb_list == null || sb_list.size() == 0) {
                map.put("x_shebei_flag", 0);
            } else {
                map.put("x_shebei_flag", 1);
            }
            if (zh_list == null || zh_list.size() == 0) {
                map.put("x_zaihai_flag", 0);
            } else {
                map.put("x_zaihai_flag", 1);
            }
            if (map.get("x_shebei_flag").toString().equals("0") && map.get("x_shebei_flag").equals("0")) {
                map.put("x_flag", 0);
            } else {
                map.put("x_flag", 1);
            }
            map.put("systime", nowtime);
            //添加数据
            if (map.get("id") == null) {
                Integer id = dao.save("zhyy_xuntang", map, PKBean.NATIVE);
                map.put("id", id);
            } else { //更新数据 清空子表数据
                dao.update("zhyy_xuntang", map);
                sql = "delete from zhyy_xuntangyichang where zhyy_xuntang_id = ?";
                dao.execute(sql, new Object[]{map.get("id")});
                sql = "delete from zhyy_xuntangyangzhi where zhyy_xuntang_id = ?";
                dao.execute(sql, new Object[]{map.get("id")});
            }
            //添加养殖对象记录 过滤掉无效数据
            if (yz_list != null && yz_list.size() > 0) {
                for (Map m : yz_list) {
                    if (m.get("x_dianwei") != null && ( !m.get("x_hssl").toString().equals("")  
                            || !m.get("x_hsgg").toString().equals("-1") 
                            || !m.get("x_hsfb").toString().equals("-1") ) ) {
                        m.put("systime", nowtime);
                        m.put("zhyy_xuntang_id", map.get("id"));
                        dao.save("zhyy_xuntangyangzhi", m, PKBean.NATIVE);
                    }
                }
            }
            //添加设备异常记录 过滤掉无效数据
            if (sb_list != null && sb_list.size() > 0) {
                for (Map m : sb_list) {
                    if (m != null && m.get("x_leibie") != null && !m.get("x_leibie").toString().equals("-1") && m.get("zhyy_yangzhiqu_id") != "" ) {
                        String yzqs[] = m.get("zhyy_yangzhiqu_id").toString().split(",");
                        for(String yzq : yzqs){
                            m.put("zhyy_yangzhiqu_id", yzq);
                            m.put("systime", nowtime);
                            m.put("zhyy_xuntang_id", map.get("id"));
                            dao.save("zhyy_xuntangyichang", m, PKBean.NATIVE);
                        }
                    }
                }
            }
            //添加灾害异常记录 过滤掉无效数据
            if (zh_list != null && zh_list.size() > 0) {
                for (Map m : zh_list) {
                    if (m != null && m.get("x_leibie") != null && !m.get("x_leibie").toString().equals("-1") && m.get("zhyy_yangzhiqu_id") != "" ) {
                        String yzqs[] = m.get("zhyy_yangzhiqu_id").toString().split(",");
                        for(String yzq :yzqs){
                            m.put("zhyy_yangzhiqu_id", yzq);
                            m.put("systime", nowtime);
                            m.put("zhyy_xuntang_id", map.get("id"));
                            dao.save("zhyy_xuntangyichang", m, PKBean.NATIVE);
                        }
                    }
                }
            }

            System.out.println(yz_list.size());
        } catch (Exception ex) {
            Logger.getLogger(XuntangIntfc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "1";
    }

    @RequestMapping(value = {"/ifs/zhyy/xuntang/list"}, method = {POST})
    @ResponseBody
    public Map List(String arg, int pageNum, int pageSize, int rowTotal) {
        ObjectMapper mapper = new ObjectMapper();
        MainDao mainDao = new MainDao();
        String sql = "SELECT zhyy_xuntang.id as mainid , zhyy_xuntang.x_year, zhyy_xuntang.x_month, zhyy_xuntang.x_day, zhyy_xuntang.x_hour, zhyy_xuntang.systime, zhyy_xuntang.x_cjriqi, zhyy_xuntang.x_shebei_flag, zhyy_xuntang.x_yangzhiduixiang_flag, zhyy_xuntang.x_zaihai_flag, zhyy_xuntang.x_flag  ";
        sql += " ,sys_userinfo.* ";
        sql += " ,zhyy_yangzhichang.* ";
        sql += " FROM zhyy_xuntang zhyy_xuntang ";
        sql += " left join sys_userinfo sys_userinfo on zhyy_xuntang.sys_userinfo_id = sys_userinfo.id ";
        sql += " left join zhyy_yangzhichang zhyy_yangzhichang on zhyy_xuntang.zhyy_yangzhichang_id = zhyy_yangzhichang.id ";
        sql += " WHERE 1=1 ";
        String sqlNum = "SELECT count(id) as num FROM zhyy_xuntang WHERE 1=1 ";
        List<Object> valuesList = new ArrayList<Object>();
        Map map = null;
        if(arg != null){
            try {
                map = mapper.readValue(arg, Map.class);
            } catch (IOException ex) {
                Logger.getLogger(XuntangIntfc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (map != null && map.size() != 0) {
            if (map.containsKey("x_cjriqi") && !"".equals(map.get("x_cjriqi"))) {
                valuesList.add(map.get("x_cjriqi"));
                sql += " and zhyy_xuntang.x_cjriqi = ?";
                sqlNum += " and x_cjriqi = ?";
            }
            if (map.containsKey("x_shebei_flag") && !"".equals(map.get("x_shebei_flag")) && !"-1".equals(map.get("x_shebei_flag")) ) {
                valuesList.add(map.get("x_shebei_flag"));
                sql += " and zhyy_xuntang.x_shebei_flag = ?";
                sqlNum += " and x_shebei_flag = ?";
            }
            if (map.containsKey("x_zaihai_flag") && !"".equals(map.get("x_zaihai_flag")) && !"-1".equals(map.get("x_zaihai_flag")) ) {
                valuesList.add(map.get("x_zaihai_flag"));
                sql += " and zhyy_xuntang.x_zaihai_flag = ?";
                sqlNum += " and x_zaihai_flag = ?";
            }
            if (map.containsKey("x_flag") && !"".equals(map.get("x_flag")) && !"-1".equals(map.get("x_zaihai_flag")) ) {
                valuesList.add(map.get("x_flag"));
                sql += " and zhyy_xuntang.x_flag = ?";
                sqlNum += " and x_flag = ?";
            }
            if (map.containsKey("zhyy_yangzhichang_id") && !"".equals(map.get("zhyy_yangzhichang_id")) ) {
                valuesList.add(map.get("zhyy_yangzhichang_id"));
                sql += " and zhyy_xuntang.zhyy_yangzhichang_id = ?";
                sqlNum += " and zhyy_yangzhichang_id = ?";
            }
        }
        sql += " order by zhyy_xuntang.x_cjriqi desc";
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
    
    @RequestMapping(value = {"/ifs/zhyy/xuntang/getYangzhi"}, method = {POST})
    @ResponseBody
    public List getYangzhi(String cjriqi) {
        MainDao dao = new MainDao();
        List relist = null;
        String sql = "select * from zhyy_xuntang where x_cjriqi = ?";   
        Map map = dao.queryForMap(sql, new Object[]{cjriqi});
        if(map != null){
            sql = "select * from zhyy_xuntangyangzhi where zhyy_xuntang_id=?";
            relist = dao.queryForList(sql, new Object[]{map.get("id")});
        }
        return relist;
    }
    
    @RequestMapping(value = {"/ifs/zhyy/xuntang/getYichang"}, method = {POST})
    @ResponseBody
    public List getYichang(String cjriqi) {
        MainDao dao = new MainDao();
        List<Map<String,Object>> relist = null;
        String sql = "select * from zhyy_xuntang where x_cjriqi = ?";   
        Map map = dao.queryForMap(sql, new Object[]{cjriqi});
        if(map != null){
            //sql = "select * from zhyy_xuntangyichang where zhyy_xuntang_id=?";
            sql = "select x_leixing ,x_leibie,concat( \"\",GROUP_CONCAT(zhyy_yangzhiqu_id),\"\") as zhyy_yangzhiqu_id from zhyy_xuntangyichang where zhyy_xuntang_id= ? group by x_leixing,x_leibie";
            relist = dao.queryForList(sql, new Object[]{map.get("id")});
            for(Map m : relist){
                if(!String.class.isInstance(m.get("zhyy_yangzhiqu_id"))){
                    m.put("zhyy_yangzhiqu_id", new String((byte[])m.get("zhyy_yangzhiqu_id") ) );
                }
            }
        }
        return relist;
    }
}
