package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSON;
import com.phantasie.demo.service.SingleModeService;
import com.phantasie.demo.utils.SessionUtil;
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
    public Msg saveData(@RequestBody Map data){
        JSON.parse(data.toString());
        //Claim name = jwtUtil.verify(token).get("name")

//        singleModeService.save();
        return MsgUtil.makeMsg(0,"success");
    }

//    @RequestMapping("/getData")
//    public Msg getData(){
//        SingleModeInfo data = new SingleModeInfo();
//        return MsgUtil.makeMsg(0,"", JSONObject.fromObject(data) );
//    }

    @RequestMapping("/newGame")
    public Msg newGame(@RequestBody String job){
        //删除旧存档，创建新的游戏
        net.sf.json.JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(-1,"Error");
        }
        singleModeService.newGame(job,auth.getInt("userId"));
        return MsgUtil.makeMsg(0,"create success");
    }
}
