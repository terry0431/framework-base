package com.os.framework.transceriver.server.handler;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 19:31
 **/
public class RTUHandler {
    //RTU channel 集合
    private static Map<String, ChannelHandlerContext> rtuChannelMap = new HashMap();

    protected static void addRtuChannel(String rtuid,ChannelHandlerContext ctx){
        rtuChannelMap.put(rtuid,ctx);
    }

    protected  static ChannelHandlerContext getRtuChannel(String rtuid){
        return rtuChannelMap.get(rtuid);
    }

}
