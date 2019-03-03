package com.os.framework.mq.server;

import com.os.framework.core.config.HostInfo;
import com.os.framework.mq.server.handler.TranscervierServerHandler;
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

/**
 * @program: framework-base
 * @description: 订阅服务
 * @author: wangbo
 * @create: 2019-03-03 17:12
 **/
public class TransceriverConnectionServer {
    //主线程池 接收客户端连接
    private EventLoopGroup bossGroup = null;
    //工作线程池 处理客户端连接
    private EventLoopGroup workerGroup = null;

    private TransceriverConnectionServer(){}

    private static TransceriverConnectionServer tserver = null;
    public static TransceriverConnectionServer getInstance(){
        if(tserver == null){
            tserver = new TransceriverConnectionServer();
        }
        return tserver;
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
                    socketChannel.pipeline().addLast(new TranscervierServerHandler()); //连接、读取处理器
                }
            });
            //TCP相关协议配置
//            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);//设置连接块的大小
//            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//使用长连接
            //描述异步回调的处理操作
            ChannelFuture future = serverBootstrap.bind(HostInfo.TRANSCERVIER_POST).sync(); //同步等待
            System.out.println("服务器已启动 监听端口为:" + HostInfo.TRANSCERVIER_POST);
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
        TransceriverConnectionServer.getInstance().run();
    }
}
