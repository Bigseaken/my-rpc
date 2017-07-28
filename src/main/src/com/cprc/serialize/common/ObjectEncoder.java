package com.cprc.serialize.common;

import com.cprc.serialize.CommonSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class ObjectEncoder extends MessageToByteEncoder {

    private CommonSerialize commonSerialize;

    public ObjectEncoder(CommonSerialize commonSerialize) {
        this.commonSerialize = commonSerialize;
    }

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(commonSerialize == null)
            throw new IllegalArgumentException(" commonSerialize must not null");
        commonSerialize.encode(msg, out);
    }
}
