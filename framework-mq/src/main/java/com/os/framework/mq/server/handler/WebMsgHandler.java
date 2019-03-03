package com.os.framework.mq.server.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 18:02
 **/
public class WebMsgHandler {
    private static ChannelHandlerContext ctx = null;
    public void init(ChannelHandlerContext ctx){
        this.ctx = ctx ;
    }

    public static void sendMsg(Object msg){
        if(ctx != null){
            ctx.writeAndFlush(msg);
        }
    }
}
