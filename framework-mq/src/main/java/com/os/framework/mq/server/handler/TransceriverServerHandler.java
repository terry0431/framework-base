package com.os.framework.mq.server.handler;

import com.os.framework.mq.transceriver.queue.WebQueue;
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
public class TransceriverServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LogManager.getLogger(TransceriverServerHandler.class);
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
//        System.out.println("[mp][tc][active]" + ctx.channel().remoteAddress());
        logger.info("[mp][tc][active]" + ctx.channel().remoteAddress());
        TransceriverMsgHandler msgHandler = new TransceriverMsgHandler();
        msgHandler.init(ctx);

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
            WebQueue.addMsg(msg.toString());
            logger.debug("[mq][tc][read]" + msg.toString() );
//            System.out.println("[mq][tc][read]" + msg.toString() );
        }catch(Exception e){
            e.printStackTrace();
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
//        cause.printStackTrace();
//        ctx.close();
    }
}
