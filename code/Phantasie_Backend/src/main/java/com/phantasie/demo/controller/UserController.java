package com.phantasie.demo.controller;

import com.phantasie.demo.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@Getter
@Setter
@RestController
@RequestMapping("/repository")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public void changeInfo(@RequestBody Map map){
        System.out.println("更新仓库");
        String jobInfo = (String) map.get("data");

        return;
    }
//    public static Map<Integer ,User> allUsers = new ConcurrentHashMap<>();
//    public static Map<Integer , List<jobInfo>> allJobInfos = new ConcurrentHashMap<>();
//

    @PostConstruct
    private void init(){
//        List<Card> cards= cardService.getAllCard();
//        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
//        return;
    }

}
