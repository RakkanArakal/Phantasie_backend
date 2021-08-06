package com.phantasie.demo.controller;


import com.phantasie.demo.entity.Card;
import com.phantasie.demo.utils.msg.StatusMsg;
import com.phantasie.demo.utils.msg.cardMsg;
import com.phantasie.demo.utils.msg.newState;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.SerializationUtils;

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

    private void getHurt(Integer effect_value, int type,int index) {

        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        List<StatusMsg> enemyBuffList = enemyStatus.getStatusList();
        List<Integer> seat0 = new LinkedList<>();
        List<Integer> seat1 = new LinkedList<>();
        List<Integer> flag = new LinkedList<>();
        int value = effect_value;
        int r = (int) (Math.random() *352324);

        for(int i = enemyBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = enemyBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()){
                case 5:{
                    switch (statusMsg.getStatusId()){
                        case 22222:{

                        }
                        break;
                    }
                }
                if (enemyStatus.getSeat() == 0) {
                    seat0.add(i);
                } else {
                    seat1.add(i);
                }
                    break;
                case 4:{

                }
                    break;
                default:
                    break;
            }
        }

        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()){
                case 6:{
                    switch (statusMsg.getStatusId()){
                        case 2:{
                            value *= 0.5;
                            statusMsg.setDuration(duration - 1);
                            if(duration == 1) nowBuffList.remove(i);
                        }
                        break;
                        case 100:{
                            if(r%5 == 0 && nowStatus.getCurJob() == 0 && nowStatus.getCurMp() < nowStatus.getMp()){
                                flag.add(100);
                            }
                            continue;
                        }
                        case 242:{
                            if(r%2 == 0) {
                                value = 0 ;
                            }
                        }
                        break;
                    }
                    if (nowStatus.getSeat() == 0) {
                        seat0.add(i);
                    } else {
                        seat1.add(i);
                    }
                }
                    break;
                case 4:{

                }
                    break;
                default:
                    break;
            }
        }
        timeStamp++;
        nowStatus.curHpChange(value);
        newState stateHp = new newState(nowStatus.getSeat(),0,true,value,nowStatus.getCurHp(),
                seat0, seat1, player[0].getStatusList(),player[1].getStatusList());

        allState.put(timeStamp, stateHp);

        for(int i = 0;i<flag.size();i++){
            switch (flag.get(i)){
                case 100:{
                    timeStamp++;
                    nowStatus.curMpChange(-1);
                    newState stateMp = new newState(nowStatus.getSeat(),1,false,1,nowStatus.getCurMp(),
                                            null,null,player[0].getStatusList(),player[1].getStatusList());
                    int newIndex = 0;
                    for(int j=0;j<nowStatus.getStatusList().size();j++)
                        if (nowStatus.getStatusList().get(j).getStatusId() == flag.get(i))
                            newIndex = j;
                    if(nowStatus.getSeat() == 0 )
                        stateMp.setSeatStatusOrder0(List.of(newIndex));
                    else
                        stateMp.setSeatStatusOrder1(List.of(newIndex));
                    allState.put(timeStamp, stateMp);
                }
                    break;
                default:
                    break;
            }
        }
    }

    private void makeHurt(Integer effect_value, int type,int statusId) {
        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        List<StatusMsg> enemyBuffList = enemyStatus.getStatusList();
        List<Integer> seat0 = new LinkedList<>();
        List<Integer> seat1 = new LinkedList<>();
        List<Integer> flag = new LinkedList<>();
        int value = effect_value;
        int r = (int) (Math.random() *352324);

        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()){
                case 5:{
                    switch (statusMsg.getStatusId()){
                        case 1:{
                            value *= 1.5;
                            statusMsg.setDuration(duration - 1);
                            if(duration == 1) nowBuffList.remove(i);
                        }
                        break;
                        case 101:{
                            value *= 2;
                        }
                        break;
                        case 103:{
                            flag.add(103);
                        }
                        break;
                        case 201:{
                            //flag.add(201);
                            continue;
                        }
                        case 202:{
                            flag.add(202);
                            continue;
                        }
                        case 203:{
                            if(r%2 == 0) {
                                flag.add(203);
                            }
                            continue;
                        }
                        case 221:{
                            flag.add(221);
                            continue;
                        }
                        case 222:{
                            //todo
                        }
                        break;
                        case 223:{
                            //todo
                        }
                        break;
                        case 241:{
                            flag.add(241);
                            //todo
                        }
                        break;


                        default:
                            break;

                    }
                    if (nowStatus.getSeat() == 0) {
                        seat0.add(i);
                    } else {
                        seat1.add(i);
                    }
                }
                    break;
                case 4:{

                }
                    break;
                default:
                    break;
            }
        }

        for(int i = enemyBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = enemyBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()){
                case 6:{
                    switch (statusMsg.getStatusId()){
                        case 2:{
                            value *= 0.5;
                            statusMsg.setDuration(duration - 1);
                            if(duration == 1) enemyBuffList.remove(i);
                        }
                        break;
                        case 100:{
                            if(r%5 == 0 && enemyStatus.getCurJob() == 0 && enemyStatus.getCurMp() < enemyStatus.getMp()){
                                flag.add(100);
                            }
                            continue;
                        }
                        case 242:{
                            if(r%2 == 0) {
                                value = 0 ;
                            }
                        }
                        break;
                    }
                }
                if (enemyStatus.getSeat() == 0) {
                    seat0.add(i);
                } else {
                    seat1.add(i);
                }
                    break;
                case 4:{

                }
                    break;
                default:
                    break;
            }

        }

        timeStamp++;
        enemyStatus.curHpChange(value);
        newState stateHp = new newState(enemyStatus.getSeat(),0,true,value,enemyStatus.getCurHp(),
                seat0,seat1,player[0].getStatusList(),player[1].getStatusList());

        allState.put(timeStamp, stateHp);

        for(int i = 0;i<flag.size();i++){
            switch (flag.get(i)){
                case 100:{
                    timeStamp++;
                    enemyStatus.curMpChange(-1);
                    newState newstate = new newState(enemyStatus.getSeat(),1,false,1,enemyStatus.getCurMp(),
                            null,null,player[0].getStatusList(),player[1].getStatusList());
                    int newIndex = 0;
                    for(int j=0;j<enemyStatus.getStatusList().size();j++)
                        if (enemyStatus.getStatusList().get(j).getStatusId() == flag.get(i))
                            newIndex = j;
                    if(enemyStatus.getSeat() == 0 )
                        newstate.setSeatStatusOrder0(List.of(newIndex));
                    else
                        newstate.setSeatStatusOrder1(List.of(newIndex));
                    allState.put(timeStamp, newstate);
                }
                    break;
                case 103:
                case 203:{
                    timeStamp++;
                    enemyStatus.curHpChange(value);
                    newState newstate = new newState(enemyStatus.getSeat(),0,true,value,enemyStatus.getCurHp(),
                            null,null,player[0].getStatusList(),player[1].getStatusList());
                    int newIndex = 0;
                    for(int j=0;j<enemyStatus.getStatusList().size();j++)
                        if (enemyStatus.getStatusList().get(j).getStatusId() == flag.get(i))
                            newIndex = j;
                    if(enemyStatus.getSeat() == 0 )
                        newstate.setSeatStatusOrder0(List.of(newIndex));
                    else
                        newstate.setSeatStatusOrder1(List.of(newIndex));

                    allState.put(timeStamp, newstate);
                }
                break;
                case 201:{

                }
                break;
                case 202:{
                    timeStamp++;
                    nowStatus.curHpChange((int) (value * (-0.5)));
                    newState newstate = new newState(nowStatus.getSeat(),0,false, (int) (value*0.5),nowStatus.getCurHp(),
                            null,null,player[0].getStatusList(),player[1].getStatusList());
                    int newIndex = 0;
                    for(int j=0;j<nowStatus.getStatusList().size();j++)
                        if (nowStatus.getStatusList().get(j).getStatusId() == flag.get(i))
                            newIndex = j;

                    if(nowStatus.getSeat() == 0 )
                        newstate.setSeatStatusOrder0(List.of(newIndex));
                    else
                        newstate.setSeatStatusOrder1(List.of(newIndex));
                    allState.put(timeStamp, newstate);
                }
                break;
                case 221: {
                    timeStamp++;
                    StatusMsg statusMsg = new StatusMsg(221);
                    int curValue = 0;
                    for(int k=0;i<enemyBuffList.size();k++) {
                        if (enemyBuffList.get(k).getStatusId() == 221) {
                            statusMsg = enemyBuffList.get(k);
                            curValue = statusMsg.getEffect_value();
                            enemyBuffList.remove(k);
                            break;
                        }
                    }

                    statusMsg.setEffect_value(curValue + (value *= 0.5));
                    enemyBuffList.add(statusMsg);
                    newState newstate = new newState(2, null,null,player[0].getStatusList(),player[1].getStatusList());
                    allState.put(timeStamp, newstate);
                }
                    break;
                case 241:{
                    if(statusId != 3) break;
                    timeStamp++;
                    StatusMsg statusMsg = new StatusMsg(241);
                    int curValue = 0;
                    for(int k=0;i<enemyBuffList.size();k++) {
                        if (enemyBuffList.get(k).getStatusId() == 241) {
                            statusMsg = enemyBuffList.get(k);
                            curValue = statusMsg.getEffect_value();
                            enemyBuffList.remove(k);
                            break;
                        }
                    }

                    statusMsg.setEffect_value(curValue + (value *= 2/3));
                    enemyBuffList.add(statusMsg);
                    newState newstate = new newState(2, null,null,player[0].getStatusList(),player[1].getStatusList());
                    allState.put(timeStamp, newstate);

                }
                break;
                default:
                    break;
            }
        }
    }

    private void getCost(Integer effect_value, int type,int index) {

        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        List<StatusMsg> enemyBuffList = enemyStatus.getStatusList();
        List<Integer> seat0 = new LinkedList<>();
        List<Integer> seat1 = new LinkedList<>();
        List<Integer> flag = new LinkedList<>();
        int value = effect_value;
        int r = (int) (Math.random() *352324);

        timeStamp++;
        nowStatus.curMpChange(value);
        newState newstate = new newState(nowStatus.getSeat(),1,true,value,nowStatus.getCurMp(),
                null,null,player[0].getStatusList(),player[1].getStatusList());

        allState.put(timeStamp, newstate);

        for(int i = 0;i<flag.size();i++){
            switch (flag.get(i)){
                case 202:{
                }
                break;
                default:
                    break;
            }
        }
    }
    private void makeStatus(Card card, int cardOrder) {
        if(card.getEmy_status() == 0 && card.getMy_status() == 0)
            return;
        int statusId;
        if(card.getEmy_status() == 0 ){
            statusId = card.getMy_status();
        }else {
            statusId = card.getEmy_status();
        }
        timeStamp++;
        StatusMsg statusMsg = new StatusMsg(statusId);
        switch (card.getCard_id()){

            case 400:{
                statusMsg.setEffect_value(600);
                enemyStatus.getStatusList().add(statusMsg);
            }
            break;
            case 201:
            case 404:{
                nowStatus.getStatusList().add(statusMsg);
            }
            break;
            default:
                break;
        }
        newState newstate = new newState(2, null,null,player[0].getStatusList(),player[1].getStatusList());
        allState.put(timeStamp, newstate);

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
            int duration = statusMsg.getDuration();

            switch (statusMsg.getEffect_phase()) {
                case 0 : {
                    switch (statusMsg.getStatusId()) {
                        case 10:                       //被瞄准
                        case 32:{                      //被点燃
                            getHurt(statusMsg.getEffect_value(),1,statusMsg.getStatusId());
                            nowBuffList.remove(i);
                        }
                        break;
                        case 35:{                       //中毒
                            getHurt(statusMsg.getEffect_value()*duration,1,statusMsg.getStatusId());
                            statusMsg.setDuration(duration-1);
                            if(duration == 1)
                                nowBuffList.remove(i);
                        }
                        break;
                        case 99:{
                            getCost(statusMsg.getEffect_value(),1,i);
                        }
                        break;
                        case 98:{
                            if(nowStatus.getCurMp() < 40) {
                                getCost(40 - nowStatus.getCurMp(), 1, i);
                            }
                        }
                        break;
                        case 9874:{
                            makeHurt(statusMsg.getEffect_value(),1,i);
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
                    statusMsg.setDuration(duration-1);
                    if(duration == 1)
                        nowBuffList.remove(i);
                }
                break;
                default:
                    break;
            }
        }
        return ;
    }

    public void getCard(int id) {

        nowStatus = player[id];

        List<Integer> cardList = nowStatus.getCardList();
        List<Integer> deckList = nowStatus.getDeckList();
        int total = nowStatus.getTurnCount();

        if(total >= 2){
            if(deckList.size() == 1 || cardList.size() == 3)
                total = 1 ;
            else
                total = 2;
        }

        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()) {
                case 1 : {

                }
                break;
                default:
                    break;
            }
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
            if(deckList.size() == 0)
                return;
        }

        return;
    }

    public int useCard(int id, int cardOrder) {


        List<Integer> cardList = nowStatus.getCardList();
        Integer cardId = cardList.get(cardOrder);
        cardList.remove(cardOrder);
        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        List<StatusMsg> enemyBuffList = enemyStatus.getStatusList();
        List<Integer> flag = new LinkedList<>();

        int r = (int) (Math.random() *352324);
        Card card = (Card) SerializationUtils.clone(allCards.get(cardId));

        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()){
                case 2:{
                    switch (statusMsg.getStatusId()){
                        case 102:{
                            if(card.getJob() != 2) break;
                            if(card.getCard_id() == 304) break;
                            card.setMy_cost((int) (card.getMy_cost()*0.5));
                            statusMsg.setDuration(duration - 1);
                            if(duration == 1) nowBuffList.remove(i);
                            //todo 非伤害卡
                        }
                        break;
                        default:
                            break;
                    }
                }
                break;
                case 4:{

                }
                break;
                default:
                    break;
            }
        }

        switch (card.getType()){
            case 0:{
                hurtCard(card,cardOrder);
            }
            break;
            case 1:{
                healCard(card,cardOrder);
            }
            break;
            case 2:{
                statusCard(card,cardOrder);
            }
            break;
            default:
                break;
        }


        if(card.getSpecial() != 0){
            timeStamp++;
            switch (card.getSpecial()){
                case 1:{
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

    private void statusCard(Card card, int cardOrder) {
        makeStatus(card,cardOrder);
        getCost(card.getMy_cost(),0,cardOrder);
        return;
    }


    private void hurtCard(Card card, int cardOrder) {
        makeHurt(card.getEmy_hp(),0,card.getJob());
        getCost(card.getMy_cost(),0,cardOrder);
        makeStatus(card,cardOrder);
        return;
    }

    private void healCard(Card card, int cardOrder) {
        getHurt(card.getMy_hp(),0,cardOrder);
        getCost(card.getMy_cost(),0,cardOrder);
        makeStatus(card,cardOrder);
        return;
    }

    public void endTurn(int id) {
        if(nowStatus != player[id]) return;

        List<StatusMsg> nowBuffList = nowStatus.getStatusList();
        for(int i = nowBuffList.size() - 1; i >= 0 ;i--){
            StatusMsg statusMsg = nowBuffList.get(i);
            int duration = statusMsg.getDuration();
            switch (statusMsg.getEffect_phase()) {
                case 3 : {
                }
                break;
                default:
                    break;
            }
        }

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

        for(int i=0;i<cardmsg.getCardList().size();i++){
            if(allCards.get(cardmsg.getCardList().get(i)).getMy_cost() <= nowStatus.getCurMp())
                cardmsg.getUsableCard().add(true);
            else
                cardmsg.getUsableCard().add(false);
        }

        JSONObject data = JSONObject.fromObject(cardmsg);
        if(!flag){
            data.replace("decks",null);
            data.replace("cardList",null);
            data.replace("usableCard",null);
        }
        return data;
    }
}
