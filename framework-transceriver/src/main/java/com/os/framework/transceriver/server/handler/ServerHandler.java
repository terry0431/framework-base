package com.os.framework.transceriver.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.os.framework.transceriver.subscribe.handler.MsgHanlder;
import com.os.framework.vo.transceriver.RtuEquipment;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: framework-base
 * @description: 服务端输入处理类
 * @author: wangbo
 * @create: 2019-02-25 19:15
 **/
public class ServerHandler  extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(ServerHandler.class);

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
        logger.info("[tc][rtu][active]{}" , ctx.channel().remoteAddress());
//        System.out.println("[tc][rtu][active]" + ctx.channel().remoteAddress() );
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
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        try {
            String tmp = msg.toString();
            if (tmp != null && tmp.indexOf("{") > -1 && tmp.indexOf("RS232") == -1) {
                RtuEquipment rtuEquipment =  JSON.parseObject(msg.toString(), new TypeReference<RtuEquipment>() {});
                RTUHandler.addRtuChannel(rtuEquipment.getRtuid(),ctx);// 判断是否已保存 保存rtuid channelid channel
                MsgHanlder.sendMsg( msg );
            }else{
                MsgHanlder.sendMsg( RTUHandler.getRCKV(ctx.channel().id().asShortText()) + "|" +  msg);
            }
            logger.debug("[tc][rtu][read]{}" , msg.toString().trim() );
//            System.out.println("[tc][rtu][read]" + msg.toString().trim() );
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            ReferenceCountUtil.release(msg);//释放缓存
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        if(RTUHandler.getRCKV(ctx.channel().id().asShortText() ) != null){
            logger.info("[tc][rtu][inactive]{}" , RTUHandler.getRCKV(ctx.channel().id().asShortText() ));
//            System.out.println("[tc][rtu][inactive]" + RTUHandler.getRCKV(ctx.channel().id().asShortText() ) );
            RTUHandler.removeRCKV(ctx.channel().id().asShortText());
        }else{
            logger.info("[tc][rtu][inactive]" + ctx.channel().remoteAddress());
//            System.out.println("[tc][rtu][inactive]" + ctx.channel().remoteAddress() );
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
//        cause.printStackTrace();
//        ctx.close();
    }
}
