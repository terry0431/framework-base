package com.os.framework.mq.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @program: framework-base
 * @description: 服务端输入处理类
 * @author: wangbo
 * @create: 2019-02-25 19:15
 **/
public class WebServerHandler extends ChannelInboundHandlerAdapter {


    /**
      * @Description:客户端连接成功时调用次方法
      * @param ctx
      * @return:void
      * @Author:wangbo
      * @Date:2019-02-25
      * @Time:19:39
    **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("已连接 ...");
        WebMsgHandler msgHandler = new WebMsgHandler();
        msgHandler.init(ctx);
//        byte data[] = "server : 已连接".getBytes();
//        //Netty 缓存类型 封装了NIO中的Buffer
//        ByteBuf buf = Unpooled.buffer(data.length);
//        buf.writeBytes(data);//将数据写入到缓存
//        ctx.writeAndFlush(buf);//强制性发送所有的数据
    }

    /**
      * @Description:客户端发送数据时调用此方法
      * @param ctx
      * @param msg
      * @return:void
      * @Author:wangbo
      * @Date:2019-02-25
      * @Time:19:39
    **/
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        try {

            System.out.println("********* " + msg.toString().trim() );
            //将消息存入队列

//            RtuEquipment equipment = (RtuEquipment)msg;
////
////            System.out.println("{服务器}" + equipment.getRtuid() );
////            equipment.setDatatime("2010-11-11");
//////            String remsg = "server : " + msg + HostInfo.SEPARATOR;
////            ctx.writeAndFlush(equipment);
        }finally{
            ReferenceCountUtil.release(msg);//释放缓存
        }
    }
    /**
      * @Description:发生异常时调用该方法
      * @param ctx
     *  @param cause
      * @return:
      * @Author:wangbo
      * @Date:2019-02-25
      * @Time:19:57
    **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
