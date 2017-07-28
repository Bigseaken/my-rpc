package com.cprc.core.execute;

import com.cprc.core.SendFrame;
import com.cprc.core.SendInitializer;
import com.cprc.core.handler.SendHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class SendExecute implements Callable<Boolean> {
    private InetSocketAddress inetSocketAddress;
    private EventLoopGroup work ;

    private String id;

    public SendExecute(String ip, int port,EventLoopGroup work){
        inetSocketAddress = new InetSocketAddress(ip, port);
        this.work = work;
    }

    public SendExecute(String ip, int port,EventLoopGroup work,String id) {
        inetSocketAddress = new InetSocketAddress(ip, port);
        this.work = work;
        this.id = id;
    }

    public SendExecute(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public Boolean call() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new SendInitializer())
                .remoteAddress(inetSocketAddress);
        final ChannelFuture future;
        try {
            future = bootstrap.connect().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future2) throws Exception {
                System.out.println("connect netty server is ok ...");
                SendHandler channel = future.channel().pipeline().get(SendHandler.class);
                SendFrame.getInstance().setChannelHandler(channel);
            }
        });


        return Boolean.TRUE;
    }
}
