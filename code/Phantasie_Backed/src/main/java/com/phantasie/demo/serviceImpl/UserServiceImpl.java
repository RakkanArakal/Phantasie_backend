package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.dao.UserDao;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.phantasie.demo.utils.SessionUtil.setSession;

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
            obj.put("userId", user.getUserId());
            obj.put("nickName",user.getNickName());
            obj.put("job",user.getJob());
            obj.put("userName", userVerify.getUsername());
            setSession(obj);
            return MsgUtil.makeMsg(0,"success",obj);
        }
        else {
            return MsgUtil.makeMsg(-1,"用户名或密码错误");
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
            obj.put("user_id", user.getUserId());
            obj.put("name", userVerify.getUsername());
            return MsgUtil.makeMsg(4,"success",obj);
        }
        else {
            return MsgUtil.makeMsg(-1,"用户名已被占用");
        }
    }

    @Override
    public User findUserByUsername(String username) {
        if (username.isEmpty()) return null;
        return userDao.findUserByUsername(username);
    }

    @Override
    public User findUserById(Integer id) {
        if (id == 0) return null;
        return userDao.findUserById(id);
    }

    @Override
    public void setToken(String token, User user){
        userDao.setToken(token, user);
        return;
    }

    @Override
    public void setJobInfo(String data, User user){
        userDao.setJobInfo(data,user);
    }

    @Override
    public void setNickname(String Nickname, User user){
        userDao.setNickname(Nickname,user);
    }
}

