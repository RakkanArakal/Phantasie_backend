package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.dao.UserDao;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgCode;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public Msg login(String username, String password) {
        UserVerify userVerify = checkUser(username, password);
        if (userVerify != null) {
            User user = findUserById(userVerify.getUser_id());
            JSONObject obj = new JSONObject();
            obj.put("user_id", user.getUser_id());
            obj.put("name", userVerify.getUsername());
            return MsgUtil.makeMsg(MsgCode.LOGIN_SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, obj);
        }
        else {
            return MsgUtil.makeMsg(MsgCode.LOGIN_USER_ERROR);
        }
    }

    private UserVerify checkUser(String username, String password) {
        UserVerify uv = userDao.checkUser(username);
        if (uv != null && uv.getPassword().equals(password)) {
            return userDao.checkUser(username);
        }
        return null;
    }

    @Override
    public Msg signup(String username, String password,String phone) {
        User user = findUserByUsername(username);
        if (user == null) {
            user = userDao.createUser(username, password,phone);
            JSONObject obj = new JSONObject();
            UserVerify userVerify = checkUser(username, password);
            obj.put("user_id", user.getUser_id());
            obj.put("name", userVerify.getUsername());
            return MsgUtil.makeMsg(MsgCode.SIGNUP_SUCCESS, MsgUtil.SIGNUP_SUCCESS_MSG, obj);
        }
        else {
            return MsgUtil.makeMsg(MsgCode.SIGNUP_USER_ERROR);
        }
    }


    private User findUserById(Integer id) {
        if (id == 0) return null;
        return userDao.findUserById(id);
    }

    private User findUserByUsername(String username) {
        if (username.isEmpty()) return null;
        return userDao.findUserByUsername(username);
    }
}

