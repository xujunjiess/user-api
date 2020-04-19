package com.lanxin.common;

public enum ResultConstant {

    SUCCESS(0, "操作成功。"),

    ERROR(1, "系统错误，请联系管理员。"),

    NO_LOGINED_IN(1001, "未登录。"),

    PERMISSION_DENIED(1002, "无修改权限。"),

    FILE_TYPE_ERROR(1003, "文件类型不支持。"),

    INVALID_LENGTH(1004, "无效长度。"),

    INVALID_PARAMETER(1005, "验证码过期。"),

    ERROR_CREATEUSER(1006, "用户创建失败。"),

    ERROR_CREATESHOP(1008, "请输入正确的城市。"),

    INVALID_PARAMETER_MOBIE(10010, "手机号和验证码不匹配。"),

    INVALID_MOBIE_ERROR(10011, "手机号输入错误。"),

    HB_STATUS_YES(10012, "红包已拆开。"),

    INVITECODE_NO_USER(10013,"不存在当前邀请码"),

    ORDER_COUNT(10014,"今日提现次数已达上限"),

    ORDER_ERROR(10015,"提现金额大于余额"),

    NO_LIST(1007,"暂无数据");

    public int code;

    public String message;

    /**
     *
     * @param code
     * @param message
     */
    ResultConstant(int code, String message) {
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
}
