/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.web.service.bundle.zhyy;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.cache.bundle.zhyy.SystemCache;
import com.os.framework.web.sys.mod.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Message {

    private static Map<String, List<Map<String, Object>>> msgmap;

    public void initMsg() {
        //key userid -> list msgid
        msgmap = new HashMap();
        MainDao dao = new MainDao();
        String sql = "select * from sys_userinfo where id > 0";
        List<Map<String, Object>> userlist = dao.queryForList(sql, null);
        List<Map<String, Object>> msglist = null;
        for (Map m : userlist) {
            if (m.get("u_leibie").toString().equals("1")) {//管理账号
                sql = " SELECT * FROM zhyy_xiaoxi t1, zhyy_yangzhichang t2, sys_role_yangzhichang t3, sys_role_user t4 WHERE "
                        + " t1.zhyy_yangzhichang_id = t3.zhyy_yangzhichang_id AND t3.sys_role_id = t4.role_id and t4.user_id=? and "
                        + " ( t1.id not in (select msg_id from zhyy_xiaoxi_zhuangtai where user_id=?) )";
                msglist = dao.queryForList(sql, new Object[]{m.get("id"), m.get("id")});

            } else {//养殖场主账号
                sql = "select * from zhyy_xiaoxi where zhyy_yangzhichang_id = ? and "
                        + " ( id not in (select msg_id from zhyy_xiaoxi_zhuangtai where user_id=?) )";
                msglist = dao.queryForList(sql, new Object[]{m.get("zhyy_yangzhichang_id"), m.get("id")});
            }
            msgmap.put(m.get("id").toString(), msglist);
        }
    }

    public void updateMsg(Map xxmap) {
        SystemCache syscache = new SystemCache();
        List<Map<String, Object>> userlist = syscache.getYzcUser(xxmap.get("zhyy_yangzhichang_id").toString());
        if (userlist != null && userlist.size() > 0) {
            for (Map m : userlist) {
                if (msgmap.get(m.get("id").toString()) == null) {
                    msgmap.put(m.get("id").toString(), new ArrayList());
                }
                msgmap.get(m.get("id").toString()).add(xxmap);
            }
        }
    }

    public List<Map<String, Object>> getMsgByUser(String userid) {
        if(userid != null && !"".equals(userid)){
            return msgmap.get(userid);
        }else{
            return null;
        }
    }

    public void updateMsgByUser(User user) {
        MainDao dao = new MainDao();
        String sql = "";
        List msglist;
        if (user.getType().equals("1")) {//管理账号
            sql = " SELECT * FROM zhyy_xiaoxi t1, zhyy_yangzhichang t2, sys_role_yangzhichang t3, sys_role_user t4 WHERE "
                    + " t1.zhyy_yangzhichang_id = t3.zhyy_yangzhichang_id AND t3.sys_role_id = t4.role_id and t4.user_id=? and "
                    + " ( t1.id not in (select msg_id from zhyy_xiaoxi_zhuangtai where user_id=?) )";
            msglist = dao.queryForList(sql, new Object[]{user.getId(), user.getId()});

        } else {//养殖场主账号
            sql = "select * from zhyy_xiaoxi where zhyy_yangzhichang_id = ? and "
                    + " ( id not in (select msg_id from zhyy_xiaoxi_zhuangtai where user_id=?) )";
            msglist = dao.queryForList(sql, new Object[]{user.getId(), user.getId()});
        }
        msgmap.put(user.getId(), msglist);
    }
}
