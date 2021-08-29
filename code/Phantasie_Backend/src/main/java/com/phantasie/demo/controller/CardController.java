package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;
import com.phantasie.demo.entity.Status;
import com.phantasie.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/database")
public class CardController {

    @Autowired
    private CardService cardService;

    public static Map<Integer ,Card> allCards = new ConcurrentHashMap<>();
    public static Map<Integer , Status> allStatus = new ConcurrentHashMap<>();


    @PostConstruct
    private void init(){
        List<Card> cards= cardService.getAllCard();
        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
        List<Status> statuses= cardService.getAllStatus();
        allStatus = statuses.stream().collect(Collectors.toMap(Status::getStatusId, a -> a,(k1, k2)->k1));
        System.out.print(allStatus);
        return;
    }

    @RequestMapping("/getCard")
    public List<Card> getDatabaseCard(){
        return cardService.getAllCard();
    }

    @RequestMapping("/getStatus")
    public List<Status> getDatabaseStatus(){
        return cardService.getAllStatus();
    }

}
