package com.phantasie.demo.utils.msgutils;

import com.phantasie.demo.utils.msg.jobInfo;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

@Getter
@Setter
public class Msg {
    private int status;
    private String msg;
    private int timeStamp;
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

    public Msg(int status, String msg, int time) {
        this.status = status;
        this.msg = msg;
        this.data = null;
        this.list = null;
        this.timeStamp = time;
    }

    public Msg(int status, String msg, JSONObject obj, JSONArray list) {
        this.status = status;
        this.msg = msg;
        this.data = obj;
        this.list = list;
    }


    public void setList(List<jobInfo> jobInfoList){
        this.list = JSONArray.fromObject(jobInfoList);
    }
}
