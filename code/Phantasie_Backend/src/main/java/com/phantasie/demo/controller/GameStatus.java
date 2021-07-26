package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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

    private int hp ;

    private int ap ;

//    private int carOrder;

    private List<Card> deckList = new LinkedList<>();

//    private List<Card> graveList = new LinkedList<>();

    private List<Card> cardList = new LinkedList<>();

    private int turnCount;

//    private int cardCount;

//    private int maxCardCount;

//    private List<status> statusList = new LinkedList<>();

    private int gameId;

    private int seat;

    private boolean isInRoom ;

//    private boolean isInTurn ;

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
        hp -= num;
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
//        graveList.clear();
        cardList.clear();
        deckList = getPlayerDeck();
        curJob = Job.Warrior;
    }

    private List<Card> getPlayerDeck() {
        List<Card> Deck = new LinkedList<>();
        Card c000 = CardController.allCards.get(0);
        Card c100 = CardController.allCards.get(100);
        Card c200 = CardController.allCards.get(200);
        System.out.print(c000);
        System.out.print(c100);
        System.out.print(c200);

        for(int i=0;i<10;i++){
            int r = (int) (Math.random ()*(352324 +1));
            if  (r % 3 == 1)
                Deck.add(c000);
            else if(r % 3 == 2)
                Deck.add(c100);
            else
                Deck.add(c200);
        }

        return Deck;
    }

    public void resetDeckList() {
        deckList = getPlayerDeck();
        return;
    }
}



