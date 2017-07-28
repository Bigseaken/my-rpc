package com.cprc.model;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 * <p>
 * socket 通讯时传输的请求对象
 */
public class Request  {
    private String id;

    private String classz;

    private Object[] arg;

    private String methodName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getClassz() {
        return classz;
    }

    public void setClassz(String classz) {
        this.classz = classz;
    }

    public Object[] getArg() {
        return arg;
    }

    public void setArg(Object[] arg) {
        this.arg = arg;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
