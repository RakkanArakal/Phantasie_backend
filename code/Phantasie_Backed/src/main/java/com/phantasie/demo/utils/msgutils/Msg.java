package com.phantasie.demo.utils.msgutils;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Getter
@Setter
public class Msg {
    private int status;
    private String msg;
    private Integer timeStamp = null;
    private JSONObject data;
    private JSONArray list = null;

    Msg(MsgCode msg, JSONObject data){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = data;
    }

    Msg(MsgCode msg, String extra, JSONObject data){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = data;
    }

    Msg(MsgCode msg){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = null;
    }

    Msg(MsgCode msg, String extra){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = null;
    }

    Msg(int status, String extra, JSONObject data){
        this.status = status;
        this.msg = extra;
        this.data = data;
    }

    Msg(int status, String extra, JSONArray list){
        this.status = status;
        this.msg = extra;
        this.data = null;
        this.list = list;
    }
    Msg(int status, String extra, JSONObject data,int timeStamp){
        this.status = status;
        this.msg = extra;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    Msg(int status, String extra, JSONArray list,int timeStamp){
        this.status = status;
        this.msg = extra;
        this.data = null;
        this.list = list;
        this.timeStamp = timeStamp;
    }

    Msg(int status, String extra){
        this.status = status;
        this.msg = extra;
        this.data = null;
    }

}
