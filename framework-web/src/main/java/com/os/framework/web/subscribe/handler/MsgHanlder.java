package com.os.framework.web.subscribe.handler;

import com.os.framework.core.config.HostInfo;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 17:51
 **/
public class MsgHanlder {
    private static final Logger logger = LogManager.getLogger(MsgHanlder.class);
    private static ChannelHandlerContext ctx = null;
    public void init(ChannelHandlerContext ctx){
        this.ctx = ctx ;
    }

    public static void sendMsg(String rtuid,Object msg){
        if(ctx != null && ctx.channel().isActive()){
            logger.debug("[web][mq][send]{}{}{}" , rtuid ,HostInfo.SEPARATOR ,msg);
//            System.out.println("[web][mq][send]" + rtuid + HostInfo.SEPARATOR + msg);
            ctx.writeAndFlush(rtuid + HostInfo.SEPARATOR + msg);
        }
    }
}
