package com.os.framework.web.subscribe;

import com.os.framework.core.config.HostInfo;
import com.os.framework.web.subscribe.handler.SubscribeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: framework-base
 * @description: 订阅客户端
 * @author: wangbo
 * @create: 2019-03-03 17:38
 **/
public class SubscribeClient {
    private static final Logger logger = LogManager.getLogger(SubscribeClient.class);
    static SubscribeClient subscribeClient = null;
    private SubscribeClient(){}
    public static SubscribeClient getInstance(){
        if(subscribeClient == null){
            subscribeClient = new SubscribeClient();
        }
        return subscribeClient;
    }
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1); // 创建一个线程池
        try {
            Bootstrap client = new Bootstrap(); // 创建客户端处理程序
            client.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true) // 允许接收大块的返回数据
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8)); //ByteBuf转String处理器
                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); //String转ByteBuf处理器
                            socketChannel.pipeline().addLast(new SubscribeHandler()); // 追加了处理器
                        }
                    });
            ChannelFuture channelFuture = client.connect(HostInfo.HOST_NAME, HostInfo.WEB_POST).sync();
            channelFuture.channel().closeFuture().sync(); // 关闭连接
        } finally {
            group.shutdownGracefully();
        }
    }
}
