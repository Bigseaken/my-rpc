package com.cprc.model;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.callback.Callback;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class CallBack implements Serializable {
   private static Logger logger = (Logger) LoggerFactory.getLogger(Callback.class);

    private String id;

    private Object data;

    private Lock lock = new ReentrantLock();
    private Condition codition = lock.newCondition();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {

        if (data != null) {
            return data;
        }

        lock.lock();
        try {
            codition.await(10000, TimeUnit.MILLISECONDS);
            logger.info("服务器处理超时");
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
        return data;
    }

    public void setData(Object data) {
        lock.lock();
        try {
            codition.signalAll();
        } finally {
            lock.unlock();
        }
        this.data = data;
    }
}
