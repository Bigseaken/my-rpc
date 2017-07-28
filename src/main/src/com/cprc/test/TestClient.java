package com.cprc.test;

import com.cprc.ProxyFactory;
import com.cprc.core.SendFrame;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        SendFrame frame = SendFrame.getInstance();
        frame.start();
        OpService opService = (OpService) ProxyFactory.getProxy(OpService.class);
        opService.sum(1,3);



       System.out.println("down");
    }
}
