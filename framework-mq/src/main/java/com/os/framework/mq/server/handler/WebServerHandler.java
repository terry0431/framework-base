package com.os.framework.mq.server.handler;

import com.os.framework.core.config.HostInfo;
import com.os.framework.mq.transceriver.queue.RTUQueue;
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
public class WebServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LogManager.getLogger(WebServerHandler.class);
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
        logger.debug("[mq][web][active]" + ctx.channel().remoteAddress());
//        System.out.println("[mq][web][active]" + ctx.channel().remoteAddress());
        WebMsgHandler msgHandler = new WebMsgHandler();
        msgHandler.init(ctx);
    }

    /**
      * @Description:客户端发送数据时调用此方法
      * @param ctx
      * @param obj
      * @return:void
      * @Author:wangbo
      * @Date:2019-02-25
      * @Time:19:39
    **/
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object obj){
        try {
            //检查指令是否正确
            if(obj == null || obj.toString().split(HostInfo.SEPARATOR).length != 2){
//                System.out.println("[mq][web][read][errmsg]" + obj);
                logger.debug("[mq][web][read][errmsg]{}" ,obj);
                return;
            }
            String rtuid = obj.toString().split(HostInfo.SEPARATOR)[0];
            String msg = obj.toString().split(HostInfo.SEPARATOR)[1];
            System.out.println("[mq][web][read]" + rtuid + " msg : " + msg.trim() );
            //建立 队列 向队列中插入消息 队列将消息转发给 收发器处理
            RTUQueue.addMsg(rtuid,msg);

        }catch(Exception e ){
            e.printStackTrace();
        }finally{
            ReferenceCountUtil.release(obj);//释放缓存
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
