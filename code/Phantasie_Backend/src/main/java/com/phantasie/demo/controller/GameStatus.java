package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;

import java.util.LinkedList;
import java.util.List;

enum Sequence{First,Second};
enum Job{Warrior,Archer,Magician};

public class GameStatus {
    private String playerId;

    private Sequence sequence;

    private int hp;

    private int ap;

    private List<Card> cardList = new LinkedList<>();

    private List<Card> graveList = new LinkedList<>();

    private int turnCount;

    private int cardCount;

    private int maxCardCount;

//    private List<status> statusList = new LinkedList<>();

    private int gameId;

    private boolean isGameOver = false;

    private Job curJob;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean useCard(int index){
        if(cardList.size() >= index+1){
            //使用卡牌的效果
            cardList.remove(index);
            return true;
        }
        return false;
    }

    public boolean checkDeath(){
        if(hp <= 0){
            hp = 0;
            isGameOver = true;
            return true;
        }else{
            return false;
        }
    }

    public void hpChange(int num){
        hp += num;
    }

    public void apChange(int num){
        ap += num;
    }

    public int getGameId() {
        return gameId;
    }

}



