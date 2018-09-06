package com.dpermi.core;

/**
 * Created by val620@126.com on 2017/10/18.
 */
public class JsonResult {

    private int code;//响应代码
    private String msg;//返回信息
    private Object obj;//返回对象

    public JsonResult(){
    }

    public JsonResult(int code,String msg,Object obj){
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
