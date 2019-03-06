package com.os.framework.transceriver.subscribe.handler;

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

    public static void sendMsg(Object msg){
        if(ctx != null){
            logger.debug("[tc][mq][send]{}" , msg);
//            System.out.println("[tc][mq][send]" + msg);
            ctx.writeAndFlush(msg);
        }
    }
}
