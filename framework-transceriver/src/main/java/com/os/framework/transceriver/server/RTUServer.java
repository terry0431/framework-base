package com.os.framework.transceriver.server;

import com.os.framework.core.config.HostInfo;
import com.os.framework.handler.serialize.FastJsonDecoder;
import com.os.framework.handler.serialize.FastJsonEncoder;
import com.os.framework.transceriver.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class RTUServer {

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
    public void run()throws Exception {

        try {
            //主线程池 接收客户端连接
            bossGroup = new NioEventLoopGroup();
            //工作线程池 处理客户端连接
            workerGroup = new NioEventLoopGroup();
            //服务端的程序类进行NIO启动 可以设置Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);//设置
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8)); //ByteBuf转String处理器
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); //String转ByteBuf处理器
                    socketChannel.pipeline().addLast(new ServerHandler()); //连接、读取处理器
                }
            });
            //TCP相关协议配置
//            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);//设置连接块的大小
//            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//使用长连接
            //描述异步回调的处理操作
            ChannelFuture future = serverBootstrap.bind(HostInfo.RTU_POST).sync(); //同步等待
            System.out.println("服务器已启动 监听端口为:" + HostInfo.RTU_POST);
            future.channel().closeFuture().sync();//等到socket被关闭
        }finally {
            //关闭连接池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown(){
        if(bossGroup != null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null){
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args)throws Exception {
        new RTUServer().run();
        //System.out.println(System.getProperty("line.separator"));
    }
}
