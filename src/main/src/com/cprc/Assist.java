package com.cprc;

import com.cprc.core.SendFrame;
import com.cprc.model.EventMsg;
import com.cprc.model.ListerMsg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by withqianqian@163.com on 2017/7/29.
 *
 * 辅助类 用于设置spring init-method destroy
 */

public class Assist {
    SendFrame sendFrame = SendFrame.getInstance();

    @PostConstruct
    public void afterPropertiesSet(){
        System.out.println("执行InitializingBean接口的afterPropertiesSet方法");
        sendFrame.registerEvent();
    }

    @PreDestroy
    public void postEvent(){
        System.out.println("执行DisposableBean接口的destroy方法");
        sendFrame.postEvent();
    }
}
