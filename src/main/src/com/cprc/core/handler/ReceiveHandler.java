package com.cprc.core.handler;

import com.cprc.model.Request;
import com.cprc.model.Respone;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class ReceiveHandler extends SimpleChannelInboundHandler<Request> {

    private ApplicationContext context;

    public ReceiveHandler() {
    }

    public ReceiveHandler(ApplicationContext context) {
        this.context = context;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        System.out.println("server accept request id is :" + msg.getId());
        String id = msg.getId();
        String classz = msg.getClassz();
        Object[] params = msg.getArg();
        String methodName = msg.getMethodName();
        if (context != null) {
            //获取对应service的实现类 实现类的id为service的名称
            //没有做service 与实现类的映射
            String serviceName = classz;
            int implNameIndex = serviceName.lastIndexOf(".");
            serviceName = serviceName.substring(implNameIndex + 1);
            serviceName = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
            Object service = context.getBean(serviceName);

            Respone respone = new Respone();
            Object result = MethodUtils.invokeMethod(service, methodName, params);

            respone.setId(id);
            respone.setResult(result);
            ctx.writeAndFlush(respone);
        } else {
            throw new IllegalArgumentException("application is not be null");
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
