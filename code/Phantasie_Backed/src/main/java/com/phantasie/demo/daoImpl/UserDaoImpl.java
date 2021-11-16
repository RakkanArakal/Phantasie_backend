package com.phantasie.demo.daoImpl;


import com.phantasie.demo.dao.UserDao;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.entity.UserVerify;
import com.phantasie.demo.repository.UserRepository;
import com.phantasie.demo.repository.UserVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public User findUserByUsername(String username) { return userRepository.findUserByName(username); }
}
