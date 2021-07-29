package com.phantasie.demo.controller;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msgutils.Msg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    public static Map<Integer ,User> allUsers = new ConcurrentHashMap<>();


    @RequestMapping("/login")
    public Msg login(@RequestBody Map<String,String> map) {
        System.out.println("用户登录请求");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
<<<<<<< HEAD
        //return userService.login(username, password);
        JSONObject obj = new JSONObject();
        obj.put("user_id", 1120);
        obj.put("name", username);
        obj.put("pass",password);
        return MsgUtil.makeMsg(MsgCode.SIGNUP_SUCCESS, MsgUtil.SIGNUP_SUCCESS_MSG, obj);
=======

        return userService.login(username, password);
>>>>>>> efaab6e (14:15)
    }

    @PostMapping(value = "/signup")
    public Msg signup(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String phone = (String) map.get("phone");

        return userService.signup(username, password,phone);
    }
}

