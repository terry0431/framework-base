package com.os.framework.web.intef.bundle.zhyy;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.os.framework.db.dao.MainDao;
import com.os.framework.quartz.jobs.bundle.zhyy.RTUMod;
import com.os.framework.web.cache.bundle.zhyy.SystemCache;
import com.os.framework.web.socket.NIOServer;
import com.os.framework.web.sys.mod.User;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
/**
 *
 * @author @version 1.0
 */
@Controller
public class GlobalIntfc {

    SystemCache syscache = new SystemCache();
    /**
     * 获取全部养殖场
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getYzclist"}, method = {POST})
    @ResponseBody
    public List<Map<String,Object>> getYzcList(HttpServletRequest request) {
        User user =  (User)request.getSession().getAttribute("userInfo");
        
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        try {
            String sql = "";
            List args = new ArrayList();
            if(user.getId().equals("-1")){ //超级管理员
                sql = "select * from zhyy_yangzhichang ";
            }
            else if(user.getType().equals("1")){ //业务人员
                sql = "SELECT distinct t2.*  FROM  zhyy_yangzhichang t2, sys_role_yangzhichang t3, sys_role_user t4 " +
                        " WHERE t3.sys_role_id = t4.role_id and t4.user_id=?";
            }else{// 养殖场账号
                sql = "select * from zhyy_yangzhichang where id=?";
                args.add(user.getYzcid() );
            }
            list = mainDao.queryForList(sql, args.toArray());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 根据养殖场获取全部养殖区
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getYzqList"}, method = {POST})
    @ResponseBody
    public List<Map<String,Object>> getYzqList(String yzcid) {
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        try {
            list = mainDao.queryForList("select * from zhyy_yangzhiqu where zhyy_yangzhichang_id=?", 
                    new Object[]{yzcid});
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 根据养殖场获取全部rtu
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getRtulistByYzc"}, method = {POST})
    @ResponseBody
    public List<Map<String,Object>> getRtulistByYzc(String yzcid) {
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        try {
            list = mainDao.queryForList("select * from zhyy_rtu where zhyy_yangzhichang_id=?", new Object[]{yzcid} );
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 根据养殖区获取带有水质数据的rtu
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getRtuListByYzq"}, method = {POST})
    @ResponseBody
    public List<Map<String,Object>> getRtuSzListByYzq(String yzqid) {
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        try {
            list = mainDao.queryForList("select distinct(zhyy_rtu_id) from zhyy_rtu_shezhi where zhyy_yangzhiqu_id=? and s_obj='zhyy_shuizhishuju'", 
                    new Object[]{yzqid} );
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * 根据RTUID获取RTU设置
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getRtuszListByRtuid"}, method = {POST})
    @ResponseBody
    public List<Map<String,Object>> getRtuszListByRtuid(String rtuid) {
        MainDao mainDao = new MainDao();
        List<Map<String,Object>> list = null;
        try {
            list = syscache.getRtuszByRtuid(rtuid);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 根据RTUID获取RTU设置
     *
     * @param
     * @return
     * @author  wangbo
     * @version 1.0
     */
    @RequestMapping(value = {"/ifs/zhyy/getRTUModById"}, method = {POST})
    @ResponseBody
    public RTUMod getRTUModById(String rtuid) {
        RTUMod rtumod ;
        if(NIOServer.rtumap.get(rtuid) != null){
            rtumod = NIOServer.rtumap.get(rtuid);
            return rtumod;
        }else{
            return null;
        }
    }
}
