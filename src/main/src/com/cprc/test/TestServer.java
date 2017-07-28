package com.cprc.test;

import com.cprc.core.execute.ReceiveExecute;

/**
 * Created by withqianqian@163.com on 2017/7/28.
 */
public class TestServer {
    public static void main(String[] args) {
        ReceiveExecute receiveExecute = new ReceiveExecute(null);
        receiveExecute.start();
    }
}
