package com.watermelon.aspect;

import java.util.Arrays;

public class RequestLog {
    private String ip;
    private String url;
    private String classMethod;
    private Object[] args;

    public RequestLog(String ip, String url, String classMethod, Object[] args) {
        this.ip = ip;
        this.url = url;
        this.classMethod = classMethod;
        this.args = args;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
