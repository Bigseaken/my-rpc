package com.cprc.core;

import com.cprc.core.handler.SendHandler;
import com.cprc.serialize.common.ObjectDecoder;
import com.cprc.serialize.common.ObjectEncoder;
import com.cprc.serialize.kryo.KryoUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class SendInitializer  extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        KryoUtils k = new KryoUtils();
        ch.pipeline().addLast(new ObjectEncoder(k));
        ch.pipeline().addLast(new ObjectDecoder(k));
        ch.pipeline().addLast(new SendHandler());
    }
}
