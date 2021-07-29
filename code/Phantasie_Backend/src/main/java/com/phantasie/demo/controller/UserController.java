package com.phantasie.demo.controller;

import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public static Map<Integer ,User> allUsers = new ConcurrentHashMap<>();

    @PostConstruct
    private void init(){
//        List<Card> cards= cardService.getAllCard();
//        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
//        return;
    }

}
