package com.phantasie.demo.service;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.utils.msgutils.Msg;

public interface UserService {
    Msg login(String username, String password);
    Msg signup(String username, String password,String phone);
    User findUserByUsername(String username);

    User findUserById(Integer id);

    void setToken(String token, User user);

    void setJobInfo(String toString, User user);

    void setNickname(String nickName, User user);

    void setJob(Integer job,User user);

    Msg getSingleMode(Integer userId);
}
