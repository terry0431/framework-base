package com.os.framework.transceriver.subscribe.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 17:51
 **/
public class MsgHanlder {
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
