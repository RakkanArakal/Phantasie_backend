package com.phantasie.demo.utils.msgutils;

public enum MsgCode {
    LOGIN_SUCCESS(MsgUtil.SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG),
    LOGIN_USER_ERROR(MsgUtil.LOGIN_USER_ERROR,MsgUtil.LOGIN_USER_ERROR_MSG),
    LOGIN_ERROR(MsgUtil.ERROR,MsgUtil.ERROR_MSG),
    NOT_LOGGED_IN_ERROR(MsgUtil.NOT_LOGGED_IN_ERROR,MsgUtil.NOT_LOGGED_IN_ERROR_MSG),

    SIGNUP_SUCCESS(MsgUtil.SUCCESS, MsgUtil.SIGNUP_SUCCESS_MSG),
    SIGNUP_USER_ERROR(MsgUtil.ERROR, MsgUtil.SIGNUP_USER_ERROR_MSG);



    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    MsgCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
