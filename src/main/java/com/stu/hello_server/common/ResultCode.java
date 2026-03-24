package com.stu.hello_server.common;


/**
 * 全局业务状态码枚举
 */
public enum ResultCode {
    // 基础状态码
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统繁忙,请稍后再试"),
    // 权限相关
    TOKEN_INVALID(401, "登录凭证已缺失或过期,请重新登录");

    private final Integer code;
    private final String msg;

    // 构造方法
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // getter方法
    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}