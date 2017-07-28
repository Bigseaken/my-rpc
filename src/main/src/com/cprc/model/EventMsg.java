package com.cprc.model;

import java.util.Date;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class EventMsg {
    private Long lastTime;
    public EventMsg(){
    }

    public long getLast () {
        return new Date().getTime();
    }
}
