package com.cprc.model;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 * 
 * socket 通讯时传输的响应对象
 */
public class Respone {


    private String id;
    /**
     * 返回结果集
     */
    private Object result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
