package com.os.framework.transceriver.server;

import com.os.framework.core.config.HostInfo;
import com.os.framework.transceriver.server.handler.RTUMsgDecoder;
import com.os.framework.transceriver.server.handler.RTUMsgEncoder;
import com.os.framework.transceriver.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RTUServer {
    private static final Logger logger = LogManager.getLogger(RTUServer.class);
    //主线程池 接收客户端连接
    EventLoopGroup bossGroup = null;
    //工作线程池 处理客户端连接
    EventLoopGroup workerGroup = null;

    private RTUServer(){}

    private static RTUServer rserver = null;
    public static RTUServer getInstance(){
        if(rserver == null){
            rserver = new RTUServer();
        }
        return rserver;
    }

    public void run(){
        try {
            //主线程池 接收客户端连接
            bossGroup = new NioEventLoopGroup(2);
            //工作线程池 处理客户端连接
            workerGroup = new NioEventLoopGroup(2);
            //服务端的程序类进行NIO启动 可以设置Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);//设置
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new RTUMsgEncoder(CharsetUtil.UTF_8)); //ByteBuf转String处理器
                    socketChannel.pipeline().addLast(new RTUMsgDecoder(CharsetUtil.UTF_8)); //String转ByteBuf处理器
                    socketChannel.pipeline().addLast(new ServerHandler()); //连接、读取处理器
                }
            });
            //TCP相关协议配置
            //描述异步回调的处理操作
            ChannelFuture future = serverBootstrap.bind(HostInfo.RTU_POST).sync(); //同步等待
            logger.info("[tc][rtu][start]port : " + HostInfo.RTU_POST);
            future.channel().closeFuture().sync();//等到socket被关闭
        }catch(Exception e){
//            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            //关闭连接池
            if(!bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully();
            }
            if(!workerGroup.isShutdown()) {
                workerGroup.shutdownGracefully();
            }
        }
    }

    public void shutdown(){
        if(bossGroup != null && !bossGroup.isShuttingDown()){
            logger.info("[tc][rtu][boss][shutdown]...");
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null && !workerGroup.isShuttingDown()){
            logger.info("[tc][rtu][work][shutdown]...");
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args)throws Exception {
        new RTUServer().run();
        //System.out.println(System.getProperty("line.separator"));
    }
}