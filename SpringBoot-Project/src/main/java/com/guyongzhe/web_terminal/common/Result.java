package com.guyongzhe.web_terminal.common;

public class Result<T> {
    //0为成功，1为失败
    private  String code;//返回的代码（前端可根据次代码判断此次响应的情况）
    private  String msg;//返回给前端的提示信息
    private T data;//返回给前端的数据，一般是一个实体类

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(String code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
