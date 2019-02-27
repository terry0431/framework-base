package com.os.framework.imitator.handler;

import com.os.framework.vo.transceriver.RtuEquipment;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

/**
 * @program: framework-base
 * @description: 模拟器处理器
 * @author: wangbo
 * @create: 2019-02-25 20:48
 **/
public class ImitatorHandler extends ChannelInboundHandlerAdapter {

    private static final int REPEAT = 500;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" ====  client channelActive ==== ");
        RtuEquipment equipment = new RtuEquipment();
        equipment.setRtuid("00010001");
        System.out.println("equipment length : " + ObjectSizeCalculator.getObjectSize(equipment));//获取对象字节长度
        ctx.writeAndFlush(equipment );
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" ====  client channelRead ==== ");
        try{
            RtuEquipment equipment = (RtuEquipment)msg;

            System.out.println("{服务器}" + equipment.getRtuid() + "date :" + equipment.getDatatime());
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}