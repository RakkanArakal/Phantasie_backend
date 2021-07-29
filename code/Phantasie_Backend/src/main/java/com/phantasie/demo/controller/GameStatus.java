package com.phantasie.demo.controller;

import com.phantasie.demo.entity.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

enum Job{Warrior,Magician,Ranger};

@Setter
@Getter

public class GameStatus implements Cloneable {

    private String playerId;

    private String playerName;

    private int hp ;

    private int ap ;


//    @JsonIgnore
    private List<Integer> deckList = new LinkedList<>();

    private List<Integer> cardList = new LinkedList<>();

//    private List<Integer> cardIndex = new LinkedList<>();

    private int turnCount;

//    private List<Card> graveList = new LinkedList<>();

//    private List<status> statusList = new LinkedList<>();

    private int gameId;

    private int seat;

    private boolean isInRoom ;

//    private boolean isInTurn ;

    private boolean isGameOver = false;

    private Job curJob;

    public void hpChange(int num){
        hp -= num;
        return;
    }

    public void apChange(int num){
        ap -= num;
        return;
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

    private List<Integer> getPlayerDeck() {
        List<Integer> Deck = new LinkedList<>();

        Card c000 = CardController.allCards.get(0);
        Card c100 = CardController.allCards.get(100);
        Card c200 = CardController.allCards.get(200);
        System.out.print(c000);
        System.out.print(c100);
        System.out.print(c200);

        for(int i=0;i<10;i++){
            int r = (int) (Math.random ()*(352324 +1));
            switch (r % 17){
                case 0 : Deck.add(0);
                    break;
                case 1 : Deck.add(100);
                    break;
                case 2 : Deck.add(200);
                    break;
                case 3 : Deck.add(201);
                    break;
                case 4 : Deck.add(202);
                    break;
                case 5 : Deck.add(203);
                    break;
                case 6 : Deck.add(204);
                    break;
                case 7 : Deck.add(300);
                    break;
                case 8 : Deck.add(301);
                    break;
                case 9 : Deck.add(302);
                    break;
                case 10 : Deck.add(303);
                    break;
                case 11 : Deck.add(304);
                    break;
                case 12 : Deck.add(400);
                    break;
                case 13 : Deck.add(401);
                    break;
                case 14 : Deck.add(402);
                    break;
                case 15 : Deck.add(403);
                    break;
                case 16 : Deck.add(404);
                    break;

            }

        }

        return Deck;
    }

    public void resetDeckList() {
        deckList = getPlayerDeck();
        return;
    }

    public Object getHands() {
        return cardList.size();
    }
}



