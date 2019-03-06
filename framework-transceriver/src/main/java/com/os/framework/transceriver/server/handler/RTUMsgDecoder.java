package com.os.framework.transceriver.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-04 09:52
 **/
public class RTUMsgDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger logger = LogManager.getLogger(RTUMsgDecoder.class);
    private final Charset charset;

    public RTUMsgDecoder() {
        this(Charset.defaultCharset());
    }

    public RTUMsgDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(msg.toString(this.charset));
    }
}
