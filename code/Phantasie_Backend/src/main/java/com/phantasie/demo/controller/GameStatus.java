package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msg.StatusMsg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@Component
public class GameStatus implements Cloneable {


    private String playerId;

    private String playerName;

    private String jobInfo;

    private int gameId;

    private int seat;

    private int curHp ;

    private int curMp ;

    private int hp;

    private int mp;

    private int curJob;

    private int buffId;

    private int skillId;

    private int coldDown;

    private int turnCount;

    private boolean isInRoom = false;

    private boolean isGameOver = false;

    private List<Integer> cardLibrary = new LinkedList<>();

    private List<Integer> deckList = new LinkedList<>();

    private List<Integer> cardList = new LinkedList<>();

    private List<StatusMsg> statusList = new LinkedList<>();

    private List<Boolean> usableCard = new LinkedList<>();

//    private List<Card> graveList = new LinkedList<>();

    public void curHpChange(int num){
        curHp -= num;
        if(curHp >= hp) curHp = hp;
        return;
    }

    public void curMpChange(int num){
        curMp -= num;
        if(curMp >= mp) curMp = mp;
        return;
    }

    public int getGameId() {
        return gameId;
    }

    public void newGame() {
        List<jobInfo> jobInfoList= JSONArray.parseArray(jobInfo,jobInfo.class);
        buffId = jobInfoList.get(curJob).getBuffId();
        skillId = jobInfoList.get(curJob).getSkillId();
        cardLibrary = jobInfoList.get(curJob).getCardLibrary();
        curHp = hp = 2000;
        turnCount = 1 ;
        coldDown = 6 ;
        cardList.clear();
        deckList = getPlayerDeck();

        switch (curJob){
            case 0 :{
                mp = 5;
                curMp = 0;
                statusList.add(new StatusMsg(100));
            }break;
            case 1 :{
                mp = 100;
                curMp = 50;
                statusList.add(new StatusMsg(98));
            }break;
            case 2 : {
                mp = 100;
                curMp = 50;
                statusList.add(new StatusMsg(99));
            }break;
        }
        statusList.add(new StatusMsg(buffId));
    }

    private List<Integer> getPlayerDeck() {
        deckList.clear();
        for (int i = 10; i > 0 ; i--) {
            int randomPos = (int) (Math.random() * (352324 + 1)) % i;
            deckList.add(cardLibrary.get(randomPos));
            cardLibrary.remove(randomPos);
        }
        for (int i = 0;i<10;i++) {
            cardLibrary.add(deckList.get(i));
        }
        return deckList;
    }
    public boolean resetDeckList() {
        if(deckList.size() != 0) return false;
        deckList = getPlayerDeck();
        return true;
    }


}



