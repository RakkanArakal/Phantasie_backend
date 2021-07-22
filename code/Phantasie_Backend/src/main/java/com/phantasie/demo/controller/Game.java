package com.phantasie.demo.controller;


import com.phantasie.demo.entity.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Game {
    private int current_player;

    public static GameStatus[] player;

    private int gameId;

    boolean isRunning = true;

    public GameStatus nowStatus ;
    public GameStatus enemyStatus ;

    public Game (Room room){
        player = room.player;
        player[0].newGame();
        player[1].newGame();
    }

    public static void changePlayer() {
        GameStatus tmp = player[0];
        player[0] = player[1];
        player[1] = tmp;
    }

    public GameStatus[] getPlayer() {
        return player;
    }

    public static final String playerStatFormat(int id,int hp,int ap){
        return
            String.format("player_%d$hp#%d$ap#%d$",id,hp,ap);
    }

    public static final String cardlistFormat(String type,List<Card> cardList){
        String form = String.format("%s",type);
        for(Card card : cardList){
            form += String.format("#%d",card.getCard_id());
        }
        return form;
    }

    public String packStat(int id){
        if(player.length != 2){
            return ("");
        }
        GameStatus toStatus = player[id];
        int enemy = (id ^ 1);

        String ret ="$";
        ret += playerStatFormat(0,player[0].getHp(),player[0].getAp());
        ret += playerStatFormat(1,player[1].getHp(),player[1].getAp());

//        ret += cardlistFormat("$Grave",toStatus.getGraveList());
        ret += String.format("deckList#%d",toStatus.getDeckList().size());
        ret += cardlistFormat("$cardList",toStatus.getCardList());
        ret += String.format("$EnemyCount#%d$",player[enemy].getCardList().size());
        return ret;
    }

    public void getCard(int id) {
        GameStatus nowStatus = player[id];

        List<Card> cardList = nowStatus.getCardList();
        List<Card> deckList = nowStatus.getDeckList();
        int total = nowStatus.getTurnCount();
        if(total >= 3)            total = 3 - cardList.size();        //抽到3张为止;
        if(deckList.size() < total)   total = deckList.size();

        for(int i=0;i<total;i++){
            cardList.add(deckList.get(0));
            deckList.remove(0);
        }

        if(deckList.size() == 0)     nowStatus.resetDeckList();

        return;
    }

    /**
     *  todo
        卡片使用
     */
    public void useCard(int id, int cardOrder) {
        nowStatus = player[id];
        enemyStatus = player[id^1];

        List<Card> cardList = nowStatus.getCardList();
        Card card = cardList.get(cardOrder);
        cardList.remove(cardOrder);
        switch (card.getType()){
            case 0:{
                normalCard(card);
            }
                break;
            case 1:{
                healCard(card);
            }
                break;
            case 2:{
                warriorCard(card);
            }
                break;
            case 3:{
                mageCard(card);
            }
                break;
            case 4:{
                hunterCard(card);
            }
                break;
            default:
                break;
        }


        if(nowStatus.getHp() <= 0 || enemyStatus.getHp() <= 0)
            isRunning = false;
        return;
    }

    private void normalCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());

        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());

        return;
    }

    private void healCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());

        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());

        return;
    }

    private void warriorCard(Card card) {
    }

    private void mageCard(Card card) {
    }

    private void hunterCard(Card card) {
    }



    public void endTurn(int id) {
        GameStatus nowStatus = player[id];
        int turn = nowStatus.getTurnCount();
        nowStatus.setTurnCount(turn+1);                 //增加回合数
        return;
    }
    public void handleGameMessage(String message){

        switch (message){

        }
    }


}
