package com.os.framework.transceriver.subscribe.handler;

import com.os.framework.core.config.HostInfo;

import com.os.framework.transceriver.server.handler.RTUHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 17:41
 **/
public class SubscribeHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(SubscribeHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        MsgHanlder msgHanlder = new MsgHanlder();
        msgHanlder.init(ctx);
//        System.out.println("[tc][mq][active]" + ctx.channel().remoteAddress());
        logger.info("[tc][mq][active]{}" , ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        // 只要服务器端发送完成信息之后，都会执行此方法进行内容的输出操作
        try {
//            System.out.println("[tc][mq][read]"+obj.toString()); // 输出服务器端的响应内容
            logger.debug("[tc][mq][read]{}",obj.toString());
            String rtuid = obj.toString().split(HostInfo.SEPARATOR)[0];
            String msg = obj.toString().split(HostInfo.SEPARATOR)[1];
            RTUHandler.sendMsg(rtuid,msg);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(obj); // 释放缓存
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
    }
}
