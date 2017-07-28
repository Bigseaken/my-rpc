package com.cprc.serialize.common;

import com.cprc.serialize.CommonSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class ObjectDecoder extends ByteToMessageDecoder {
    private CommonSerialize commonSerialize;

    public ObjectDecoder(CommonSerialize commonSerialize) {
        this.commonSerialize = commonSerialize;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(commonSerialize == null)
            throw new IllegalArgumentException(" commonSerialize must not null");
        commonSerialize.decode(in, out);
    }

    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //不清除定制解码器
    }
}
