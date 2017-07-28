package com.cprc.serialize;

import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public interface CommonSerialize {
    void encode(Object msg, ByteBuf out);

    void decode(ByteBuf in, List<Object> out);

}
