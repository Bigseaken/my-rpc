package com.cprc.core.execute;

import com.cprc.core.ReceiveInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.context.ApplicationContext;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class ReceiveExecute {

    private ApplicationContext context;

    private int port;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup work = new NioEventLoopGroup();

    public ReceiveExecute(ApplicationContext context){
        this.context = context;
    }

    public void start() {
        final int newPort = getPort();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ReceiveInitializer(context));
        ChannelFuture future;
        try {
            future = bootstrap.bind(newPort).sync();
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("the netty server is ok , port is :" + newPort);
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public int getPort(){
        if(port == 0){
            port = 9090;
        }
        return  port;
    }
}
