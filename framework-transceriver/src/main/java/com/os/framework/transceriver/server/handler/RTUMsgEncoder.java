package com.os.framework.transceriver.server.handler;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-03-04 09:47
 **/
public class RTUMsgEncoder extends MessageToMessageEncoder<CharSequence> {
    private static final Logger logger = LogManager.getLogger(RTUMsgEncoder.class);
    private final Charset charset;
    public RTUMsgEncoder() {
        this(Charset.defaultCharset());
    }

    public RTUMsgEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        if (msg.length() != 0) {
            out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
        }
    }
}
