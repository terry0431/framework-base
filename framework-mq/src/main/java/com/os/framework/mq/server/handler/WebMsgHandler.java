package com.os.framework.mq.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 18:02
 **/
public class WebMsgHandler {
    private static final Logger logger = LogManager.getLogger(WebMsgHandler.class);
    private static ChannelHandlerContext ctx = null;
    public void init(ChannelHandlerContext ctx){
        this.ctx = ctx ;
    }

    public static void sendMsg(Object msg){
        if(ctx != null){
            logger.debug("[mq][web][send]{}" , msg);
//            System.out.println("[mq][web][send]" + msg);
            ctx.writeAndFlush(msg);
        }
    }
}
