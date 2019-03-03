package com.os.framework.web.intef.menber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.os.framework.db.dao.Dao;
import com.os.framework.db.dao.MainDao;
import com.os.framework.db.util.PKBean;
import com.os.framework.web.service.web.MenberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: framework-base
 * @description: 会员接口
 * @author: wangbo
 * @create: 2019-03-01 09:30
 **/
@Controller
public class MenberIntfc {
    @RequestMapping(value = {"/ifs/web/menber/apply"},method = POST ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String apply(String arg, HttpServletRequest request){
        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        if (!arg.isEmpty()) {
            try {
                Dao dao = new MainDao();
                map = mapper.readValue(arg, Map.class);
                if(map.get("id") != null) {
                    map.put("z_state",0);//等待审核状态
                    ((MainDao) dao).update("web_menber", map);
                    return "1";
                }else{
                    return "对象为空";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return ex.getMessage();
            }
        }
        return "arg为空";
    }

    @RequestMapping(value = "ifs/web/menber/getMenber", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMenber(String code){
        MainDao dao = new MainDao();
        MenberService service = new MenberService();
        Map map = service.getMenberByCode(code);
        System.out.println("====== 获取getMenber 成功" + map.get("openid"));
        if(map.get("openid") == null ){
            return null;
        }
        Map zhyymap = null;
        //查看是否有注册过
        String sql = "select count(*) as cnum from web_menber where z_weixinid = ?";
        try {
            long count = (Long) dao.queryForMap(sql, new Object[]{map.get("openid")}).get("cnum");
            //添加会员
            if (count == 0) {
                zhyymap = new HashMap();
                zhyymap.put("z_weixinid", map.get("openid"));
                zhyymap.put("z_touxiang", map.get("headimgurl"));
                zhyymap.put("z_sheng", map.get("province"));
                zhyymap.put("z_shi", map.get("city"));
                zhyymap.put("z_bieming", map.get("nickname"));
                zhyymap.put("z_state", -1);// 新用户 状态为 未申请
                zhyymap.put("id",dao.save("web_menber", zhyymap, PKBean.NATIVE) );
            } else {
                zhyymap = dao.queryForMap("select * from web_menber where z_weixinid=?", new Object[]{map.get("openid")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zhyymap;
    }
}
