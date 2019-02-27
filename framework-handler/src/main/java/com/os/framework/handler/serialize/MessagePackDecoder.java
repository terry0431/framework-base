package com.os.framework.handler.serialize;

import com.os.framework.vo.transceriver.RtuEquipment;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @program: framework-base
 * @description: massagepack序列化解码器
 * @author: wangbo
 * @create: 2019-02-27 13:41
 **/
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> list) throws Exception {

        int len = msg.readableBytes();
        System.out.println("decode msg length : " + len);
        byte[] data = new byte[len];
        msg.getBytes(msg.readerIndex(),data,0,len);
        MessagePack msgpack = new MessagePack();
//        list.add(msgpack.read(data));
        list.add(msgpack.read(data, msgpack.lookup(RtuEquipment.class)));

    }
}
