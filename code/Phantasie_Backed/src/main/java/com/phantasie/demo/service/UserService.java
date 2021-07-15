package com.phantasie.demo.service;

import com.phantasie.demo.utils.msgutils.Msg;

public interface UserService {
    Msg login(String username, String password);
    Msg signup(String username, String password);
}
