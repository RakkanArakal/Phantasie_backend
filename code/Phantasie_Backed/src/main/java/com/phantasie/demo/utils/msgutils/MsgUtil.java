package com.phantasie.demo.utils.msgutils;

import net.sf.json.JSONObject;

public class MsgUtil {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_USER_ERROR = -100;
    public static final int NOT_LOGGED_IN_ERROR = -101;

    public static final String SUCCESS_MSG = "成功！";
    public static final String ERROR_MSG = "错误！";
    public static final String LOGIN_SUCCESS_MSG = "登录成功！";
    public static final String LOGIN_USER_ERROR_MSG = "用户名或密码错误，请重新输入！";
    public static final String NOT_LOGGED_IN_ERROR_MSG = "登录失效，请重新登录！";

    public static final String SIGNUP_SUCCESS_MSG = "注册成功！";
    public static final String SIGNUP_USER_ERROR_MSG = "用户名已存在，请重新输入用户名！";

    public static final String LOGOUT_SUCCESS_MSG = "登出成功！";
    public static final String LOGOUT_ERR_MSG = "登出异常！";

    public static final String ADD_CART_SUCCESS_MSG = "加入购物车成功！";
    public static final String ADD_CART_INVENTORY_SUCCESS_MSG = "加入购物车成功，但数量不足！";
    public static final String ADD_CART_INVENTORY_ERROR_MSG = "加入购物车失败，库存不足！";
    public static final String ADD_CART_BOOK_ERROR_MSG = "加入购物车失败，书籍不存在！";

    public static final String ALTER_CART_SUCCESS_MSG = "商品数量更改成功！";
    public static final String ALTER_CART_CART_ERROR_MSG = "商品数量更改失败，购物车信息不存在！";
    public static final String ALTER_CART_INVENTORY_ERROR_MSG = "商品数量更改失败，库存不足！";
    public static final String ALTER_CART_BOOK_ERROR_MSG = "商品数量更改失败，商品已失效！";

    public static final String DEL_CART_SUCCESS_MSG = "删除商品成功！";
    public static final String DEL_CART_CART_ERROR_MSG = "删除商品失败，购物车信息不存在！";

    public static final String PURCHASE_CART_SUCCESS_MSG = "购买商品成功！";
    public static final String PURCHASE_CART_CART_ERROR_MSG = "购买商品失败，购物车信息不存在！";
    public static final String PURCHASE_CART_INVENTORY_ERROR_MSG = "购买商品失败，库存不足！";
    public static final String PURCHASE_CART_BOOK_ERROR_MSG = "购买商品失败，商品已失效！";

    public static Msg makeMsg(MsgCode code, JSONObject data){
        return new Msg(code, data);
    }

    public static Msg makeMsg(MsgCode code, String msg, JSONObject data){
        return new Msg(code, msg, data);
    }

    public static Msg makeMsg(MsgCode code){
        return new Msg(code);
    }

    public static Msg makeMsg(MsgCode code, String msg){
        return new Msg(code, msg);
    }

    public static Msg makeMsg(int status, String msg, JSONObject data){
        return new Msg(status, msg, data);
    }

    public static Msg makeMsg(int status, String msg){
        return new Msg(status, msg);
    }
}
