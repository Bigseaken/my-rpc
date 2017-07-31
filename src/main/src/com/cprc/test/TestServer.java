package com.cprc.test;

import com.cprc.Assist;
import com.cprc.core.execute.ReceiveExecute;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jndi.support.SimpleJndiBeanFactory;

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
