package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.msg.jobInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

enum Job{Warrior,Magician,Ranger};

@Setter
@Getter

public class GameStatus implements Cloneable {

    @Autowired
    UserService userService;

    private String playerId;

    private String playerName;

    private int userId;

    private int curHp ;

    private int curMp ;

    private int hp;

    private int mp;

    private int buffId;

    private int skillId;

    private List<Integer> cardLibrary = new LinkedList<>();

    private List<Integer> deckList = new LinkedList<>();

    private List<Integer> cardList = new LinkedList<>();

    private List<Integer> statusList = new LinkedList<>();

    private List<Integer> usableCard = new LinkedList<>();

    private int turnCount;

//    private List<Card> graveList = new LinkedList<>();

    private int gameId;

    private int seat;

    private boolean isInRoom = false;

    private boolean isGameOver = false;

    private int curJob;

    public void curHpChange(int num){
        curHp -= num;
        return;
    }

    public void curMpChange(int num){
        curMp -= num;
        return;
    }

    public int getGameId() {
        return gameId;
    }

    public void newGame() {

        User user = userService.findUserById(userId);
        List<jobInfo> jobInfoList= JSONArray.parseArray(user.getJobInfo(),jobInfo.class);
        buffId = jobInfoList.get(curJob).getBuffId();
        skillId = jobInfoList.get(curJob).getSkillId();
        cardLibrary = jobInfoList.get(curJob).getCardLibrary();
        curHp = hp = 2000;
        turnCount = 1 ;
        cardList.clear();
        switch (curJob){
            case 0 :{
            mp = 5;
            curMp = 0;
            }break;
            case 1 :
            case 2 : {
                curMp = mp = 100;
            }break;
        }
        deckList = getPlayerDeck();
    }
    private List<Integer> getPlayerDeck() {
        deckList.clear();
        for (int i = 10; i > 0 ; i--) {
            int randomPos = (int) (Math.random() * (352324 + 1)) % i;
            deckList.add(cardLibrary.get(randomPos));
            cardLibrary.remove(randomPos);
        }
        return deckList;
    }
    public void resetDeckList() {
        deckList = getPlayerDeck();
        return;
    }
}



