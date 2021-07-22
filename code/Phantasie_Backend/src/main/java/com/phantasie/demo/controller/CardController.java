package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;
import com.phantasie.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.phantasie.demo.entity.Card;
import com.phantasie.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    public static Map<Integer , Card> allCards = new ConcurrentHashMap<>();

    @PostConstruct
    private void init(){
        List<Card> cards= cardService.getAllCard();
        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
        return;
    }


    @GetMapping("/getbook")
    public List<Card> getCards(){
        return cardService.getAllCard();
    }

}
