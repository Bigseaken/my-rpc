package com.cprc.test;

import com.cprc.core.execute.ReceiveExecute;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class TestServer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ReceiveExecute receiveExecute = new ReceiveExecute(context);
        receiveExecute.start();
    }
}
