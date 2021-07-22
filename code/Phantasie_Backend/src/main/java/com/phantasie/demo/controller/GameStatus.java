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
public class GameStatus implements Cloneable {

    private String playerId;

    private String playerName;

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

    private boolean isInRoom ;

    private boolean isInTurn ;

    private boolean isGameOver = false;

    private Job curJob;

    @Override
    public Object clone() {
        GameStatus gameStatus = null;
        try{
            gameStatus = (GameStatus)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return gameStatus;
    }

    public void hpChange(int num){
        hp += num;
        return;
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
            int r = (int) (Math.random ()*(352324 +1));
            if(r % 2 == 1)
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


    public void setRoomOwner(int rid) {
        gameId = rid;
        seat = 0 ;
        isInRoom = true;
    }
}



