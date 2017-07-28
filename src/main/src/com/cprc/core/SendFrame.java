package com.cprc.core;

import com.cprc.core.execute.SendExecute;
import com.cprc.core.handler.SendHandler;
import com.google.common.util.concurrent.*;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class SendFrame {

    private final static SendFrame sendFrame = new SendFrame();

    private ConcurrentHashMap<String, SendHandler> channelHandlerMap = new ConcurrentHashMap<String, SendHandler>();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ListeningExecutorService pool = MoreExecutors.listeningDecorator(executorService);

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private BlockingQueue<SendHandler> queue = new ArrayBlockingQueue<SendHandler>(10);
    private BlockingQueue<Channel> queue2 = new ArrayBlockingQueue<Channel>(10);


    private SendFrame() {
    }

    public static SendFrame getInstance() {
        return sendFrame;
    }

    static final EventLoopGroup work = new NioEventLoopGroup();

    public void start() {
        ListenableFuture future = pool.submit(new SendExecute("127.0.0.1", 9090, work));
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean result) {
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
                stopLoopGroup();
            }

        });
    }



    public void setChannelHandler(SendHandler channelHandler) {
        try {
            queue.put(channelHandler);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SendHandler getChannelHandler() {
        try {
            return queue.poll(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setChannelHandler(Channel channel) {
        try {
            queue2.put(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        try {
            return queue2.poll(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void stopLoopGroup() {
        work.shutdownGracefully();
        pool.shutdown();
    }

}
