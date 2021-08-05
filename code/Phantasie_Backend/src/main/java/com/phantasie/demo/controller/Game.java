package com.phantasie.demo.controller;


import com.phantasie.demo.entity.Card;
import com.phantasie.demo.utils.msg.cardMsg;
import com.phantasie.demo.utils.msg.newState;
import com.phantasie.demo.utils.msg.StatusMsg;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.phantasie.demo.controller.CardController.allCards;


@Setter
@Getter
public class Game {
    private int current_player;

    private int gameId;

    private int timeStamp;

    private int msgCount;

    private int playerNow = 0;

    boolean isRunning = true;

    public GameStatus nowStatus ;

    public GameStatus enemyStatus ;

    public  GameStatus[] player = new GameStatus[2];

    public Map<Integer, newState> allState = new ConcurrentHashMap<>();


    public JSONArray packStat(int id){
        if(player.length != 2){
            return (null);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"deckList","jobInfo","cardLibrary"});
        List<JSONObject> jsonObjectList = new LinkedList<>();

        JSONObject data0=JSONObject.fromObject(player[id],jsonConfig);
        data0.put("decksSize",player[id].getDeckList().size());
        jsonObjectList.add(data0);

        JSONObject data1=JSONObject.fromObject(player[id^1],jsonConfig);
        data1.remove("cardList");
        data1.put("hands",player[id].getCardList().size());
        jsonObjectList.add(data1);

        return JSONArray.fromObject(jsonObjectList);
    }

    private void getHurt(Integer effect_value, int i) {

    }

    private void makeHurt(Integer effect_value, int i) {
    }



    public void gameBegin(int id){
        if(player.length != 2){
            return ;
        }
        nowStatus = player[id];
        enemyStatus = player[id^1];

        int coldDown = player[id].getColdDown();
        if(coldDown != 0) player[id].setColdDown(coldDown-1);

        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        List<StatusMsg> enemyBuffList = enemyStatus.getStatusList();

        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            switch (statusMsg.getEffect_phase()) {
                case 0 : {
                    switch (statusMsg.getStatusId()) {
                        case 10:                       //被瞄准
                        case 32:{                      //被点燃
                            getHurt(statusMsg.getEffect_value(),i);
                            nowBuffList.remove(i);
                        }
                            break;
                        case 35:{                       //中毒
                            int duration = statusMsg.getDuration();
                            getHurt(statusMsg.getEffect_value()*duration,i);
                            statusMsg.setDuration(duration-1);
                            if(duration == 1)
                                nowBuffList.remove(i);
                        }
                            break;
                        case 99:{

                        }
                            break;
                        case 9874:{
                            makeHurt(statusMsg.getEffect_value(),i);
                        }
                            break;
                        default:
                            break;
                    }
                }
                    break;
                default:
                    break;
            }

            switch (statusMsg.getDurative()){
                case 2:{

                }
                    break;
                default:
                    break;
            }
        }


        return ;
    }

    public void getCard(int id) {

        List<Integer> cardList = nowStatus.getCardList();
        List<Integer> deckList = nowStatus.getDeckList();
        int total = nowStatus.getTurnCount();

        if(total >= 2){
            if(deckList.size() == 1 || cardList.size() == 3)
                total = 1 ;
            else
                total = 2;
        }

        for(int i=0;i<total;i++){
            cardList.add(deckList.get(0));
            deckList.remove(0);
        }

        if(deckList.size() == 0)     nowStatus.resetDeckList();

        return;
    }


    public void getCard(int id,int cnt) {

        List<Integer> cardList = nowStatus.getCardList();
        List<Integer> deckList = nowStatus.getDeckList();

        if(deckList.size() == 0) nowStatus.resetDeckList();

        for(int i=0;i<cnt;i++){
            cardList.add(deckList.get(0));
            deckList.remove(0);
        }

        return;
    }

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
        if(card.getEmy_hp() != 0 ) {
            timeStamp++;
            newState stateHp = new newState(id ^ 1, 0, true, card.getEmy_hp(), enemyStatus.getCurHp());
            allState.put(timeStamp, stateHp);
        }

        if(card.getMy_hp() != 0 ) {
            timeStamp++;
            newState stateHp = new newState(id, 0, false, card.getMy_hp(), nowStatus.getCurHp());
            allState.put(timeStamp, stateHp);
        }

        if(card.getMy_cost() != 0 ) {
            timeStamp++;
            newState stateMp = new newState(id, 1, true, card.getMy_cost(), nowStatus.getCurHp());
            allState.put(timeStamp, stateMp);
        }

        if(card.getSpecial() != 0){
            timeStamp++;
            switch (card.getSpecial()){
                case 1:{
                    getCard(id,1);
                    newState stateGet = new newState(1);
                    allState.put(timeStamp,stateGet);
                    break;
                }
                default:
                    break;
            }
        }


        if(nowStatus.getCurHp() <= 0 || enemyStatus.getCurHp() <= 0)
            isRunning = false;

        return cardId;
    }

    private void normalCard(Card card) {

        nowStatus.curHpChange(card.getMy_hp());
        nowStatus.curMpChange(card.getMy_cost());
        enemyStatus.curHpChange(card.getEmy_hp());
        enemyStatus.curMpChange(card.getEmy_cost());
        return;
    }

    private void healCard(Card card) {

        nowStatus.curHpChange(-card.getMy_hp());
        nowStatus.curMpChange(card.getMy_cost());
        enemyStatus.curHpChange(-card.getEmy_hp());
        enemyStatus.curMpChange(card.getEmy_cost());
        return;
    }

    private void warriorCard(Card card) {

        nowStatus.curHpChange(card.getMy_hp());
        nowStatus.curMpChange(card.getMy_cost());
        enemyStatus.curHpChange(card.getEmy_hp());
        enemyStatus.curMpChange(card.getEmy_cost());
        return;
    }

    private void mageCard(Card card) {

        nowStatus.curHpChange(card.getMy_hp());
        nowStatus.curMpChange(card.getMy_cost());
        enemyStatus.curHpChange(card.getEmy_hp());
        enemyStatus.curMpChange(card.getEmy_cost());
        return;
    }

    private void hunterCard(Card card) {

        nowStatus.curHpChange(card.getMy_hp());
        nowStatus.curMpChange(card.getMy_cost());
        enemyStatus.curHpChange(card.getEmy_hp());
        enemyStatus.curMpChange(card.getEmy_cost());
        return;
    }

    public void endTurn(int id) {
        if(nowStatus != player[id]) return;
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
        playerNow = 0 ;
        msgCount = 0 ;
    }


    public JSONArray stageChange(int time) {
        if(time == timeStamp)
            return null;
        else{

            List<newState> newStateList= new LinkedList<>();
            while(time != timeStamp){
                time ++ ;
                if(allState.get(time).getSpecial() != 0)
                    continue;
                else
                    newStateList.add(allState.get(time));
            }
            JSONArray data=JSONArray.fromObject(newStateList);
            return data;
        }
    }


    public JSONObject cardMsg(boolean flag) {
        cardMsg cardmsg = new cardMsg(nowStatus.getCardList().size(),nowStatus.getDeckList().size(),nowStatus.getCardList());
        JSONObject data = JSONObject.fromObject(cardmsg);
        if(!flag){
            data.replace("decks",null);
            data.replace("cardList",null);
        }
        return data;
    }
}
