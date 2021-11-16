package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.service.SingleModeService;
import com.phantasie.demo.utils.SessionUtil;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SingleModeController {

    @Autowired
    private SingleModeService singleModeService;

    @RequestMapping("/saveData")
    public Msg saveData(@RequestBody String str){
        net.sf.json.JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(-1,"Error");
        }

        SingleMode singleMode = JSONObject.parseObject(str,SingleMode.class);
        singleMode.setUser_id(auth.getInt("userId"));

        jobInfo myjobinfo = JSONObject.parseObject(singleMode.getJobInfo(),jobInfo.class);
        singleMode.setJsonArray(net.sf.json.JSONObject.fromObject(myjobinfo).toString());

        singleModeService.save(singleMode);
        return MsgUtil.makeMsg(0,"success");
    }

//    @RequestMapping("/getData")
//    public Msg getData(){
//        SingleModeInfo data = new SingleModeInfo();
//        return MsgUtil.makeMsg(0,"", JSONObject.fromObject(data) );
//    }

    @RequestMapping("/newGame")
    public Msg newGame(@RequestBody Map<String,String> data){
        //删除旧存档，创建新的游戏
        net.sf.json.JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(-1,"Error");
        }
        return singleModeService.newGame(data.get("jobIndex"),auth.getInt("userId"));
    }
}
