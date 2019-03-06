package com.os.framework.mq.server;

import com.os.framework.core.config.HostInfo;
import com.os.framework.mq.server.handler.WebServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @program: framework-base
 * @description: 订阅服务
 * @author: wangbo
 * @create: 2019-03-03 17:12
 **/
public class WebConnectionServer {
    private static final Logger logger = LogManager.getLogger(WebConnectionServer.class);

    //主线程池 接收客户端连接
    static EventLoopGroup bossGroup = null;
    //工作线程池 处理客户端连接
    static EventLoopGroup workerGroup = null;
    static ChannelFuture future = null;
    private WebConnectionServer(){}

    private static WebConnectionServer sserver = null;
    public static WebConnectionServer getInstance(){
        if(sserver == null){
            sserver = new WebConnectionServer();
        }
        return sserver;
    }
    public void run() {

        try {
            //主线程池 接收客户端连接
            bossGroup = new NioEventLoopGroup(1);
            //工作线程池 处理客户端连接
            workerGroup = new NioEventLoopGroup(1);
            //服务端的程序类进行NIO启动 可以设置Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);//设置
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8)); //ByteBuf转String处理器
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); //String转ByteBuf处理器
                    socketChannel.pipeline().addLast(new WebServerHandler()); //连接、读取处理器
                }
            });
            //描述异步回调的处理操作
            future = serverBootstrap.bind(HostInfo.WEB_POST).sync(); //同步等待
//            System.out.println("[mq][WebConServer][start]port:" + HostInfo.WEB_POST);
            logger.info("[mq][WebConServer][start]port:" + HostInfo.WEB_POST);
            future.channel().closeFuture().sync();//等到socket被关闭
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //关闭连接池
            logger.info("===============close webserver=============");
//            System.out.println("===============close webserver=============");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown(){

        if(bossGroup != null){
            logger.info("[mq][web][boss][shutdown] ..." );
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null){
            logger.info("[mq][web][work][shutdown] ..." );
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args)throws Exception {
        WebConnectionServer.getInstance().run();
    }
}
