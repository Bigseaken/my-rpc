package com.cprc.core.handler;

import com.cprc.core.SendFrame;
import com.cprc.model.CallBack;
import com.cprc.model.EventMsg;
import com.cprc.model.Request;
import com.cprc.model.Respone;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class SendHandler extends SimpleChannelInboundHandler<Respone> {
    private ConcurrentHashMap<String, CallBack> callBackMap = new ConcurrentHashMap<String, CallBack>();

    private Channel channel;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    protected void channelRead0(ChannelHandlerContext ctx, Respone msg) throws Exception {
        Object result = msg.getResult();
        CallBack callBack = callBackMap.get(msg.getId());
        callBackMap.remove(msg.getId());
//        if(callBackMap.isEmpty()){
//            SendFrame.getInstance().postEvent();
//        }
        callBack.setId(msg.getId());
        callBack.setData(result);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public CallBack sendReqest(Request request) {

        CallBack callBack = new CallBack();
        callBackMap.put(request.getId(),callBack);
        channel.writeAndFlush(request);
        return callBack;
    }
}
