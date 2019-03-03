package com.os.framework.web.subscribe.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.os.framework.vo.transceriver.RtuEquipment;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-03 17:41
 **/
public class SubscribeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        MsgHanlder msgHanlder = new MsgHanlder();
        msgHanlder.init(ctx);
        System.out.println(" web server 已经 连接到 mq server ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 只要服务器端发送完成信息之后，都会执行此方法进行内容的输出操作
        try {
            //获取消息
            RtuEquipment rtuEquipment =  JSON.parseObject(msg.toString(), new TypeReference<RtuEquipment>() {});
            System.out.println(msg.toString()); // 输出服务器端的响应内容
            System.out.println("rtuid : " + rtuEquipment.getRtuid());
        } finally {
            ReferenceCountUtil.release(msg); // 释放缓存
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
    }
}
