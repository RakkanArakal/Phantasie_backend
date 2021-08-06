package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class cardMsg {

    private Integer hands;

    private Integer decks;

    private List<Integer> cardList;

    private List<Boolean> usableCard;

    public cardMsg(Integer h, Integer d, List<Integer> c){
        hands = h;
        decks = d;
        this.cardList = c;
    }
}

