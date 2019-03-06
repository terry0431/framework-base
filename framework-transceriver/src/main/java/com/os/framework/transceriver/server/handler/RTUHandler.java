package com.os.framework.transceriver.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 19:31
 **/
public class RTUHandler {
    private static final Logger logger = LogManager.getLogger(RTUHandler.class);
    //RTU channel 集合
    private static Map<String, String> channelkv = new HashMap();
    private static Map<String, ChannelHandlerContext> rtuChannelMap = new HashMap();

    protected static void addRtuChannel(String rtuid, ChannelHandlerContext ctx) {
        if (channelkv.get(ctx.channel().id().asShortText()) == null) {
            rtuChannelMap.put(rtuid, ctx);
            channelkv.put(ctx.channel().id().asShortText(), rtuid);
//            System.out.println("channelid :" + ctx.channel().id().asShortText());
        }
    }

    protected static void addRtuKv(String channelid, String rtuid) {
        channelkv.put(channelid, rtuid);
    }

    protected static ChannelHandlerContext getRtuChannel(String rtuid) {
        return rtuChannelMap.get(rtuid);
    }

    protected static String getRCKV(String channelid) {
        return channelkv.get(channelid);
    }

    protected static String removeRCKV(String channelid) {
        return channelkv.remove(channelid);
    }

    public static void sendMsg(String rtuid, String msg) {
        logger.debug("[tc][rtu][send]{}:{}" , rtuid,msg);
//        System.out.println("[tc][rtu][send]" + rtuid + ":" + msg);
        if(getRtuChannel(rtuid) != null) {
            getRtuChannel(rtuid).writeAndFlush(msg);
        }
    }
}