package com.lanxin.common;

import java.io.Serializable;

public class ApiResult extends Result implements Serializable {

    public ApiResult(){}
    /**
     *
     * @param code
     * @param message
     */
    public ApiResult(int code, String message) {
        super(code, message);
    }

    /**
     *
     * @param code
     * @param message
     * @param data
     */
    public ApiResult(int code, String message, Object data) {
        super(code, message, data);
    }

    public ApiResult(int code,Object data) {
        super(code,data);
    }
    /**
     *
     * @param resultConstant
     * @param data
     */
    public ApiResult(ResultConstant resultConstant, Object data) {
        super(resultConstant.getCode(),data);
    }

    public ApiResult(ResultConstant resultConstant, String message) {
        super(resultConstant.getCode(), message);
    }





}
