/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.web.cache.zhyy;

import com.os.framework.db.dao.MainDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class SystemCache {

    private static Map<String, List<Map<String, Object>>> yzcUserMap = null;
    private static Map<String,Map<String,Object>> yzqMap = new HashMap();
    private static Map<String, List<Map<String, Object>>> rtusz = new HashMap();
    private static Map<String, List<Map<String, Object>>> scsz = new HashMap();
    private static Map<String,List<Map<String ,Object>>> baojingshezhi = null;
    

    //初始化养殖场关联账户缓存
    public void init_yzcUser() {
        yzcUserMap = new HashMap();
        MainDao dao = new MainDao();
        String sql = "select * from zhyy_yangzhichang";
        List<Map<String, Object>> list = dao.queryForList(sql, null);
        List<Map<String, Object>> l;
        for (Map m : list) {
            sql = "select  distinct a.* from sys_userinfo a,sys_role b,sys_role_yangzhichang c,sys_role_user d "
                    + " where (a.id = d.user_id and b.id = d.role_id and c.sys_role_id = b.id and c.zhyy_yangzhichang_id = ? ) "
                    + " or a.zhyy_yangzhichang_id = ?";
            l = dao.queryForList(sql, new Object[]{m.get("id"), m.get("id")});
            yzcUserMap.put(m.get("id").toString(), l);
        }
    }
    public void init_baojingsz(){
        baojingshezhi = new HashMap();
        MainDao dao = new MainDao();
        String sql = "select * from zhyy_rtu ";
        List<Map<String,Object>> rtulist = dao.queryForList(sql, null);
        List bjszlist = null;
        for(Map rtumap : rtulist){
            sql = "select a.*,b.s_mingcheng,b.zhyy_yangzhiqu_id,b.s_attr ,d.y_mingcheng,d.y_lianxiren,d.y_lianxidianhua from zhyy_baojingshezhi a "
                    + " left join zhyy_rtu_shezhi b "
                    + " on a.zhyy_rtu_id=b.zhyy_rtu_id and a.b_tongdao=b.s_tongdao and a.b_tongdaoleibie=b.s_leibie "
                    + " left join zhyy_rtu c "
                    + " on a.zhyy_rtu_id = c.id "
                    + " left join zhyy_yangzhichang d"
                    + " on c.zhyy_yangzhichang_id = d.id "
                    + " where a.zhyy_rtu_id=? ";
            bjszlist = dao.queryForList(sql, new Object[]{rtumap.get("id")});
            if(bjszlist != null && bjszlist.size() > 0){
                baojingshezhi.put(rtumap.get("id").toString(),bjszlist);
            }
        }
    }

    /**
     * 根绝养殖场id获取有管理权限的账户
     * @param yzcid
     * @return
     */
    public List getYzcUser(String yzcid) {
        if (yzcUserMap != null && yzcUserMap.get(yzcid) != null) {
            return yzcUserMap.get(yzcid);
        } else {
            return null;
        }
    }
    
    public List getBjsz(String rtuid) {
        return baojingshezhi.get(rtuid);
    }
    /**
     * 根据智控设备id 获取该设备的设置
     * @param rtuid
     * @return
     */
    public List getRtuszByRtuid(String rtuid) {
        if (rtusz.get(rtuid) == null) {
            MainDao dao = new MainDao();
            List list = dao.queryForList("select * from zhyy_rtu_shezhi where zhyy_rtu_id=? order by zhyy_yangzhiqu_id, s_leibie,s_tongdao",
                    new Object[]{rtuid});
            rtusz.put(rtuid, list);
        }
        return rtusz.get(rtuid);
    }
    
    public Map getYzq(String yzqid){
        if(yzqMap.get(yzqid) == null){
            MainDao dao = new MainDao();
            String sql = "select * from zhyy_yangzhiqu ";
            List<Map<String,Object>> list = dao.queryForList(sql, null);
            for(Map m : list){
                yzqMap.put(m.get("id").toString(), m);
            }
        }
        return yzqMap.get(yzqid);
    }
    
    
    /**
     * 重新加载缓存
     */
    public void initCatch() {
        rtusz =new HashMap();
        init_baojingsz();
    }
}
