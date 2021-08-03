package com.phantasie.demo.daoImpl;


import com.phantasie.demo.dao.UserDao;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;
import com.phantasie.demo.repository.UserRepository;
import com.phantasie.demo.repository.UserVerifyRepository;
import com.phantasie.demo.utils.msg.jobInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVerifyRepository userVerifyRepository;

    @Override
    public UserVerify checkUser(String username) { return userVerifyRepository.checkUser(username); }

    @Override
    public User createUser(String name, String password,String phone) {
        User user = new User();
        user.setNickName(name);

        List<jobInfo> jobInfoList = jobInfo.initJob();
        JSONArray data = JSONArray.fromObject(jobInfoList);
        user.setJobInfo(data.toString());

        System.out.print(data.toString());
        userRepository.saveAndFlush(user);

        System.out.println("new user "+user.getUserId().toString()+" named "+name);
        UserVerify userVerify = new UserVerify();
        userVerify.setUser_id(user.getUserId());
        userVerify.setUsername(name);
        userVerify.setPassword(password);
        userVerify.setPhone(phone);
        userVerifyRepository.saveAndFlush(userVerify);

        return user;
    }

    @Override
    public User findUserById(Integer id) { return userRepository.findUserById(id); }

    @Override
    public User findUserByUsername(String username) {
        UserVerify userVerify = userVerifyRepository.findUserByName(username);
        if(userVerify == null) return null;
        return userRepository.findUserById(userVerify.getUser_id());
    }

    @Override
    public void setToken(String token, User user){
        user.setToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setJobInfo(String data, User user){
        user.setJobInfo(data);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setNickname(String nickname,User user){
        user.setNickName(nickname);
        userRepository.saveAndFlush(user);
    }
}
