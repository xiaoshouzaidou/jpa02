package com.lwc.common;

/**
 * Created by Administrator on 2019/8/28.
 */
public class Respon {
    private int code;
    private String msg="";
    private Object data = "";

    public Respon(){
        super();
    this.code = Constant.CODE_ERROR;
    this.msg = Constant.CODE_ERROR_STRING;
    }
    public Respon(int code,String msg){
        super();
        this.code = code;
        this.msg = msg;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
