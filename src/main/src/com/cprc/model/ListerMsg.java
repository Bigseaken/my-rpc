package com.cprc.model;

import com.cprc.core.SendFrame;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class ListerMsg {
    private EventBus bus;


    public ListerMsg(EventBus bus) {
        this.bus = bus;
    }

    @Subscribe
    public void closeThread(EventMsg msg) {
        bus.unregister(msg);
        SendFrame.getInstance().stopLoopGroup();

    }
}
