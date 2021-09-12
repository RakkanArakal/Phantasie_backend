package com.phantasie.demo.controller;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.SessionUtil;
import com.phantasie.demo.utils.TokenUtil;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgCode;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public Msg login(@RequestBody Map<String,String> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
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

<<<<<<< HEAD
        return userService.login(username, password);
>>>>>>> efaab6e (14:15)
=======
        Msg ret = userService.login(username, password);
        if(ret.getStatus() == -1) {
            return ret;
        }
        User user = userService.findUserByUsername(username);
        Integer id = user.getUserId();

        String token = TokenUtil.generate();
        userService.setToken(token,user);
        ret.getData().put("token",token);

        String jobInfoStr = user.getJobInfo();
        List<jobInfo> jobInfoList = com.alibaba.fastjson.JSONArray.parseArray(jobInfoStr,jobInfo.class);
        ret.setList(jobInfoList);

        Websocket.insertToken(token,id);
        return ret;
>>>>>>> 367b954 (17:33)
    }

    @PostMapping(value = "/signup")
    public Msg signup(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String phone = (String) map.get("phone");
        return userService.signup(username, password,phone);
    }

    @RequestMapping(value = "/test")
    public String TestFunc() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return TokenUtil.generate();
    }

    @RequestMapping("/checkSession")
    public Msg checkSession(){
        JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(MsgCode.NOT_LOGGED_IN_ERROR);
        }
        else{
            return MsgUtil.makeMsg(MsgCode.LOGIN_SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, auth);
        }
    }
}

