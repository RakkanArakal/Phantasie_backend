package com.phantasie.demo.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class cardMsg {
    Integer hands;

    private List<Integer> cardList = new LinkedList<>();

    public cardMsg(Integer h, List<Integer> c){
        hands = h;
        cardList = c;
    }
}

