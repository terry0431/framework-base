package com.os.framework.transceriver.server;

import com.os.framework.core.config.HostInfo;
import com.os.framework.transceriver.server.handler.ServerHandler;
import com.os.framework.transceriver.vo.RtuEquipment;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @program: framework-base
 * @description: 服务端
 * @author: wangbo
 * @create: 2019-02-25 19:06
 **/
public class Server {
    /**
      * @Description:服务端启动
      * @param
      * @return:
      * @Author:wangbo
      * @Date:2019-02-25
      * @Time:20:00
    **/
    public void run()throws Exception {
        //主线程池 接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程池 处理客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务端的程序类进行NIO启动 可以设置Channel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);//设置
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ByteBuf delimiter = Unpooled.copiedBuffer(HostInfo.SEPARATOR.getBytes() );

//                    socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(200)); //设置文件块
//                    //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024)); //拆包器
//                    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
//                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
//                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    socketChannel.pipeline().addLast(new ObjectEncoder() );
                    socketChannel.pipeline().addLast(new ServerHandler()); //追加处理器
                }
            });
            //TCP相关协议配置
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);//设置连接块的大小
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//使用长连接
            //描述异步回调的处理操作
            ChannelFuture future = serverBootstrap.bind(HostInfo.POST).sync(); //同步等待
            System.out.println("服务器已启动 监听端口为:" + HostInfo.POST);
            future.channel().closeFuture().sync();//等到socket被关闭
        }finally {
            //关闭连接池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args)throws Exception {
        new Server().run();
        //System.out.println(System.getProperty("line.separator"));
    }
}
