package com.os.framework.imitator;

import com.os.framework.handler.serialize.*;
import core.config.HostInfo;
import com.os.framework.imitator.handler.ImitatorHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * @program: framework-base
 * @description: JMRTU模拟器
 * @author: wangbo
 * @create: 2019-02-25 20:40
 **/
public class JmRtuImitator {

    JmRtuImitator(){
        int im_num = 10;
    }

    public void run() throws Exception {

        EventLoopGroup cgroup = new NioEventLoopGroup(1);
        try{
            Bootstrap client = new Bootstrap();
            client.group(cgroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)   //允许接收大块数据
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected  void initChannel(SocketChannel socketChannel)throws Exception{
//                            ByteBuf delimiter = Unpooled.copiedBuffer(HostInfo.SEPARATOR.getBytes() );
//                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(200)); //设置文件块
//                            //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024)); //拆包器
//                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
//                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
//                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
//                            socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
//                            socketChannel.pipeline().addLast(new ObjectEncoder() );

                            /*socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,4));
                            socketChannel.pipeline().addLast(new MessagePackDecoder());
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(4));
                            socketChannel.pipeline().addLast(new MessagePackEncoder());*/

                            /*socketChannel.pipeline().addLast(MarshalingFactory.buildMarshallingEncoder()); //
                            socketChannel.pipeline().addLast(MarshalingFactory.buildMarshallingDecoder()); //*/

                            //FastJson  序列化
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,4));
                            socketChannel.pipeline().addLast(new FastJsonDecoder()); //
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(4));  //设置后客户端无法建立连接
                            socketChannel.pipeline().addLast(new FastJsonEncoder()); //
                            socketChannel.pipeline().addLast(new ImitatorHandler());
                        }
                    });
            ChannelFuture future = client.connect(HostInfo.HOST_NAME,HostInfo.POST).sync();
            future.addListener(new GenericFutureListener() {
                @Override
                public void operationComplete(Future future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("服务器连接完成 ...");
                    }
                }
            });
            future.channel().closeFuture().sync();
        }finally {
            cgroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new JmRtuImitator().run();
    }
}