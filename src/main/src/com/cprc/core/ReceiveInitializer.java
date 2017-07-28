package com.cprc.core;

import com.cprc.core.handler.ReceiveHandler;
import com.cprc.serialize.common.ObjectDecoder;
import com.cprc.serialize.common.ObjectEncoder;
import com.cprc.serialize.kryo.KryoUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.context.ApplicationContext;


/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class ReceiveInitializer extends ChannelInitializer<SocketChannel> {

    private ApplicationContext context;

    public ReceiveInitializer(ApplicationContext context) {
        this.context = context;
    }


    protected void initChannel(SocketChannel ch) throws Exception {
        KryoUtils k = new KryoUtils();
        ch.pipeline().addLast(new ObjectEncoder(k));
        ch.pipeline().addLast(new ObjectDecoder(k));
        ch.pipeline().addLast(new ReceiveHandler(context));
    }
}
