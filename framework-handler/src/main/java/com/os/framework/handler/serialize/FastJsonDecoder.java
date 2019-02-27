package com.os.framework.handler.serialize;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-02-27 15:57
 **/
public class FastJsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> list) throws Exception {
        int len = msg.readableBytes();
        byte[] data = new byte[len];
        msg.getBytes(msg.readerIndex() ,data,0,len);
        list.add(JSON.parseObject(new String(data) ) );
    }
}
