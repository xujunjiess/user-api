package com.lanxin.common;

import java.io.Serializable;

public class Result implements Serializable{

    public Result(){}

    public int code;

    public String message;

    public Object data;

    /**
     *
     * @param code
     * @param message
     * @param data
     */
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, Object data){
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }
    /**
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
