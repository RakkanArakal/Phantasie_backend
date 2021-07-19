package com.phantasie.demo.controller;

public class Game {
    private int current_player;

   // private int target_player;

    private final GameStatus[] player = new GameStatus[2];

    private int gameId;


    boolean isRunning = true;


//    public void setTarget_player(int target_player) {
//        this.target_player = target_player;
//    }

    public GameStatus[] getPlayer() {
        return player;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void run(){

    }
}
