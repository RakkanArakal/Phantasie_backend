package com.phantasie.demo.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phantasie.demo.entity.Card;
import com.phantasie.demo.msg.cardMsg;
import com.phantasie.demo.msg.newState;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.phantasie.demo.controller.CardController.allCards;


@Setter
@Getter
public class Game {
    private int current_player;

    public  GameStatus[] player = new GameStatus[2];

    public Map<Integer, newState> allState = new ConcurrentHashMap<>();

    @JsonIgnore
    private int gameId;

    private int timeStamp;

    boolean isRunning = true;

    public GameStatus nowStatus ;

    public GameStatus enemyStatus ;

    public GameStatus[] getPlayer() {
        return player;
    }


    public String packStat(int id){
        if(player.length != 2){
            return ("");
        }

        JSONObject data=JSONObject.fromObject(player[id]);
        data.remove("deckList");
        data.put("decksSize",player[id].getDeckList().size());
        return data.toString();
    }

    public String packStatsimple(int id){
        if(player.length != 2){
            return ("");
        }
        JSONObject data=JSONObject.fromObject(player[id]);
        data.remove("cardList");
        data.remove("deckList");
        data.put("hands",player[id].getCardList().size());
        return data.toString();
    }

    public void getCard(int id) {

        nowStatus = player[id];
        enemyStatus = player[id^1];

        List<Integer> cardList = nowStatus.getCardList();
        List<Integer> deckList = nowStatus.getDeckList();
        int total = nowStatus.getTurnCount();
        if(total >= 3)            total = 3 - cardList.size();        //抽到3张为止;
        if(deckList.size() < total)   total = deckList.size();

        for(int i=0;i<total;i++){
            cardList.add(deckList.get(0));
            deckList.remove(0);
        }

        if(deckList.size() == 0)     nowStatus.resetDeckList();

//        timeStamp++;
        return;
    }

    /**
     *  todo
        卡片使用
     */
    public int useCard(int id, int cardOrder) {

//        nowStatus = player[id];
//        enemyStatus = player[id^1];

        List<Integer> cardList = nowStatus.getCardList();
        Integer cardId = cardList.get(cardOrder);
        cardList.remove(cardOrder);

        Card card = allCards.get(cardId);
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

        timeStamp++;
        newState stateHp = new newState(id^1,0,true,card.getEmy_hp(),enemyStatus.getHp());
        allState.put(timeStamp,stateHp);

        timeStamp++;
        newState stateMp = new newState(id,1,false,card.getMy_cost(),nowStatus.getAp());
        allState.put(timeStamp,stateMp);

        if(nowStatus.getHp() <= 0 || enemyStatus.getHp() <= 0)
            isRunning = false;

        return cardId;
    }

    private void normalCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());
        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());
        return;
    }

    private void healCard(Card card) {

        nowStatus.hpChange(-card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());
        enemyStatus.hpChange(-card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());
        return;
    }

    private void warriorCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());
        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());
        return;
    }

    private void mageCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());
        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());
        return;
    }

    private void hunterCard(Card card) {

        nowStatus.hpChange(card.getMy_hp());
        nowStatus.apChange(card.getMy_cost());
        enemyStatus.hpChange(card.getEmy_hp());
        enemyStatus.apChange(card.getEmy_cost());
        return;
    }

    public void endTurn(int id) {
        GameStatus nowStatus = player[id];
        int turn = nowStatus.getTurnCount();
        nowStatus.setTurnCount(turn+1);                 //增加回合数
        return;
    }

    public void start(int rid) {
        player[0].newGame();
        player[1].newGame();
        player[0].setSeat(0);
        player[1].setSeat(1);
        setGameId(rid);
        timeStamp = 0 ;
    }


    public String stageChange(int time) {
        String ret = "";
        if(time == timeStamp)
            return ret;
        else{
            ret += "$";
            List<newState> newStateList= new LinkedList<>();
            while(time != timeStamp){
                time ++ ;
                newStateList.add(allState.get(time));
            }
            JSONArray data=JSONArray.fromObject(newStateList);
            ret += data.toString() ;
            return ret;
        }
    }


    public String cardMsg(boolean flag) {
        cardMsg cardmsg = new cardMsg(nowStatus.getCardList().size(),nowStatus.getDeckList().size(),nowStatus.getCardList());
        JSONObject data = JSONObject.fromObject(cardmsg);
        if(!flag){
            data.replace("decks",null);
            data.replace("cardList",null);
        }
        return data.toString();
    }
}
