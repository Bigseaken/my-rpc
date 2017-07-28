package com.cprc.test;

import com.cprc.ProxyFactory;
import com.cprc.core.SendFrame;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        SendFrame frame = SendFrame.getInstance();
        OpService opService = (OpService) ProxyFactory.getProxy(OpService.class);
        for(int i=0;i<100;i++){
            frame.start("127.0.0.1",9090);
            new Thread(new TestThread(opService,i,i+2)).start();
        }


       System.out.println("down");
    }

    static class TestThread implements Runnable{

        OpService opService;

        int i;
        int j;

        public TestThread(OpService opService,int i ,int j){
            this.opService = opService;
            this.i = i;
            this.j = j;
        }


        public void run() {
            System.out.println(Thread.currentThread().getName()+"->"+"{"+i+","+j+"}"+opService.sum(i,j));
        }


    }
}
