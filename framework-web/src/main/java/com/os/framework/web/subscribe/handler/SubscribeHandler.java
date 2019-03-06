package com.os.framework.web.subscribe.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.os.framework.vo.transceriver.RtuEquipment;
import com.os.framework.web.handler.zhyy.RTUHandler;
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
        logger.info("[web][mq][active]{}" , ctx.channel().remoteAddress());
//        System.out.println("[web][mq][active]" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            System.out.println("[web][mq][read]"+msg);
            logger.debug("[web][mq][read]{}" , msg);
            RTUHandler rtuHandler = new RTUHandler();
            rtuHandler.builderMsg(msg.toString());
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
    }
}
