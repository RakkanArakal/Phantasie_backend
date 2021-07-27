package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class cardMsg {

    private Integer hands;

    private Integer decks;

    private List<Integer> cardList = new LinkedList<>();

    public cardMsg(Integer h,Integer d, List<Integer> c){
        hands = h;
        decks = d;
        cardList = c;
    }
}

