package com.personal.base.service.sms.aliyun.model;

public class SmsSendResult {
    private int code;
    private String message;

    public SmsSendResult(int code,String message){
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
