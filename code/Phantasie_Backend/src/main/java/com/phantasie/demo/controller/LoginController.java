package com.phantasie.demo.controller;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;
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
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    public static Map<Integer ,User> allUsers = new ConcurrentHashMap<>();
    public static Map<Integer ,List<jobInfo>> allJobInfos = new ConcurrentHashMap<>();



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
        if(ret.getStatus() == -100) {
            return ret;
        }
        User user = userService.findUserByUsername(username);
        Integer id = user.getUserId();

        if(allUsers.get(id) != null && allJobInfos.get(id) != null){
            String token = allUsers.get(id).getUserVerify().getToken();
            ret.getData().put("token",token);
            return ret;
        }
        UserVerify userVerify = user.getUserVerify();
        userVerify.setToken(TokenUtil.generate());
        allUsers.put(id,user);
        String jobInfoStr = user.getJobInfo();
        List<jobInfo> jobInfoList = com.alibaba.fastjson.JSONArray.parseArray(jobInfoStr,jobInfo.class);
        allJobInfos.put(id,jobInfoList);
        String token = allUsers.get(id).getUserVerify().getToken();
        ret.getData().put("token",token);
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

