package com.phantasie.demo.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class cardMsg {

    Integer hands;

    Integer decks;

    private List<Integer> cardList = new LinkedList<>();

    public cardMsg(Integer h,Integer d, List<Integer> c){
        hands = h;
        decks = d;
        cardList = c;
    }
}

