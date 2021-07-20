package com.phantasie.demo.dao;


import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;

public interface UserDao {
    UserVerify checkUser(String username);
    User createUser(String name, String password,String phone);
    User findUserById(Integer id);
    User findUserByUsername(String username);
}
