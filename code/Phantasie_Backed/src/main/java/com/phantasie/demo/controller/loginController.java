package com.phantasie.demo.controller;

import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public class loginController {
    @RestController
    @RequestMapping("/api")
    public class LoginController {
        @Autowired
        UserService userService;

        @RequestMapping("/login")
        public Msg login(@RequestBody Map map) {
            String username = (String) map.get("username");
            String password = (String) map.get("password");
            return userService.login(username, password);
        }

        @PostMapping(value = "/signup")
        @ResponseBody
        public Msg signup(@RequestBody Map map) {
            String username = (String) map.get("username");
            String password = (String) map.get("password");
            return userService.signup(username, password);
        }
    }
}
