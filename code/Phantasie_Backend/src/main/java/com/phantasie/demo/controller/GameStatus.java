package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

enum Sequence{First,Second};
enum Job{Warrior,Archer,Magician};

@Setter
@Getter
public class GameStatus {
    private String playerId;

    private Sequence sequence;

    private int hp ;

    private int ap ;

    private List<Card> deckList = new LinkedList<>();

    private List<Card> graveList = new LinkedList<>();

    private List<Card> cardList = new LinkedList<>();

    private int turnCount;

    private int cardCount;

    private int maxCardCount;

//    private List<status> statusList = new LinkedList<>();

    private int gameId;

    private int seat;

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
            Card card = cardList.get(index);

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

    public Sequence getSequence() {
        return sequence;
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

    public void newGame() {
        hp = 2000;
        ap = 100;
        turnCount = 1 ;
        graveList.clear();
        cardList.clear();
        deckList = getPlayerDeck();
    }

    private List<Card> getPlayerDeck() {
        List<Card> Deck = new LinkedList<>();;
        Card attack = new Card();
        Card fireball = new Card();
        attack.setCard_id(0);
        fireball.setCard_id(1);

        for(int i=0;i<10;i++){
            Random r = new Random(1);
            if(r.nextInt(1) == 1 )
                Deck.add(fireball);
            else
                Deck.add(attack);
        }

        return Deck;
    }

    public void resetDeckList() {
        deckList = getPlayerDeck();
        return;
    }
}



