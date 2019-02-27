package com.os.framework.handler.serialize;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-02-27 15:54
 **/
public class FastJsonEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        byte[] data = JSONObject.toJSONString(msg).getBytes();
        byteBuf.writeBytes(data);
    }
}
