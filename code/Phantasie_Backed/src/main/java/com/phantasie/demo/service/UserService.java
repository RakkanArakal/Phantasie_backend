package com.phantasie.demo.service;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.utils.msgutils.Msg;

public interface UserService {
    Msg login(String username, String password);
    Msg signup(String username, String password,String phone);
    User findUserByUsername(String username);

    void setToken(String token, Integer id);

    void setJobInfo(String toString, User user);
}
