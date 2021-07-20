package com.phantasie.demo.controller;

import com.phantasie.demo.entity.UserVerify;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgCode;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public Msg login(@RequestBody Map<String,String> map) {
        System.out.println("用户登录请求");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        //return userService.login(username, password);
        JSONObject obj = new JSONObject();
        obj.put("user_id", 1120);
        obj.put("name", username);
        obj.put("pass",password);
        return MsgUtil.makeMsg(MsgCode.SIGNUP_SUCCESS, MsgUtil.SIGNUP_SUCCESS_MSG, obj);
    }

    @PostMapping(value = "/signup")
    @ResponseBody
    public Msg signup(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String phone = (String) map.get("phone");
        return userService.signup(username, password,phone);
    }
}

