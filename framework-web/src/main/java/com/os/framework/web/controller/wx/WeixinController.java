package com.os.framework.web.controller.wx;

import com.os.framework.core.util.wx.SignUtil;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wangbo
 * @date 2016-12-29 12:02:24
 */
@Controller
public class WeixinController {

//    private static final Logger log = Logger.getLogger(WeixinController.class);

    @RequestMapping(value = "/weixin", method = GET)
    public String WeiXin(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");

        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        try {
            System.out.println("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
//                log.info("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
                System.out.println(echostr);
                response.getWriter().println(echostr);
            } else {
                response.getWriter().println("验证 失败 ");
                System.out.println("验证 失败 ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
