package com.cprc.core;

import com.cprc.core.execute.SendExecute;
import com.cprc.core.handler.SendHandler;
import com.cprc.model.EventMsg;
import com.cprc.model.ListerMsg;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.*;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class SendFrame {



    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private ListeningExecutorService pool = MoreExecutors.listeningDecorator(executorService);


    private  BlockingQueue<SendHandler> queue = new ArrayBlockingQueue<SendHandler>(1000);

    private  EventBus eventBus = new EventBus();

    private SendFrame() {
    }

    public static SendFrame getInstance() {

        return Inner.INSTANCE;
    }

    static final EventLoopGroup work = new NioEventLoopGroup();

    public void start(String ip,int port) {

        ListenableFuture future = pool.submit(new SendExecute(ip, port, work));
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
            SendHandler sendHandler = queue.poll(10000, TimeUnit.MILLISECONDS);
            return sendHandler;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerEvent(){
        eventBus.register(new ListerMsg(eventBus));
    }

    public void postEvent(){
        eventBus.post(new EventMsg());
    }

    public void stopLoopGroup() {
        work.shutdownGracefully();
        pool.shutdown();
    }

    private static class Inner{
       private final static SendFrame INSTANCE = new SendFrame();
    }

}
