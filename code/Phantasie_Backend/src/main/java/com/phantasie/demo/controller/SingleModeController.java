package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSON;
import com.phantasie.demo.utils.msg.SingleModeInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SingleModeController {
    @RequestMapping("/saveData")
    public Msg saveData(@RequestBody Map data){
        JSON.parse(data.toString());
        //Claim name = jwtUtil.verify(token).get("name")
        return MsgUtil.makeMsg(0,"success");
    }

    @RequestMapping("/getData")
    public Msg getData(){
        SingleModeInfo data = new SingleModeInfo();
        return MsgUtil.makeMsg(0,"", JSONObject.fromObject(data) );
    }

    @RequestMapping("/newGame")
    public Msg newGame(){
        //删除旧存档，创建新的游戏
        return MsgUtil.makeMsg(0,"create success");
    }
}
