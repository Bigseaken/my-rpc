package com.cprc;

import com.cprc.core.SendFrame;
import com.cprc.core.handler.SendHandler;
import com.cprc.model.CallBack;
import com.cprc.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class ProxyFactory implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

    private ProxyFactory(){}
    public static Object getProxy(Class... interfacec){
        if(interfacec != null){
            return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(),interfacec,new ProxyFactory());
        }else {
            logger.info("don't have interface ,that can't product proxy object");
            return  null;
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String requestId = getReqestId();
        Request request = new Request();
        request.setId(requestId);
        request.setMethodName(method.getName());
        request.setArg(args);
        request.setClassz(method.getDeclaringClass().getName());
        SendFrame frame = SendFrame.getInstance();
        SendHandler sendHandler =  frame.getChannelHandler();
        CallBack callBack = sendHandler.sendReqest(request);

        Object result = callBack.getData();
        return result;
    }

    private String getReqestId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
