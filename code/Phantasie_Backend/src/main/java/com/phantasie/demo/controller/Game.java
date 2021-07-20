package com.phantasie.demo.controller;


import com.phantasie.demo.entity.Card;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;


@Setter
@Getter
public class Game {
    public static Map<Integer,Card> AllCards;

    private int current_player;

   // private int target_player;

    private final GameStatus[] player = new GameStatus[2];

    private int gameId;


    boolean isRunning = true;


//    public void setTarget_player(int target_player) {
//        this.target_player = target_player;
//    }

    public GameStatus[] getPlayer() {
        return player;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCurrent_player() {
        return current_player;
    }

    public void run(){

    }

    public void initGame(){

    }

    public static final String playerStatFormat(int id,int hp,int ap){
        return
            String.format("player_%d$hp#%d$ap#%d$",id,hp,ap);
    }

    public static final String cardlistFormat(String type,List<Card> cardList){
        String form = String.format("%s#",type);
        for(Card card : cardList){
            form += String.format("%d#",card.getCard_id());
        }
        form += "$";
        return form;
    }

    public String packStat(int id){
        if(player.length != 2){
            return ("");
        }
        GameStatus nowStatus = player[current_player];
        int enemy = (current_player ^ 1);
        String ret ="/";
        ret += playerStatFormat(1,player[0].getHp(),player[0].getAp());
        ret += "&";
        ret += playerStatFormat(2,player[1].getHp(),player[1].getAp());
        ret += String.format("Remain#%d",nowStatus.getRemain().size());
        ret += cardlistFormat("Card",nowStatus.getCardList());
        ret += cardlistFormat("Grave",nowStatus.getGraveList());
        ret += String.format("EnemyCount#%d$/",player[enemy].getCardList().size());
        return ret;
    }



    public void handleGameMessage(String message){


        switch (message){

        }
    }


}
