package com.phantasie.demo.controller;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Room implements Serializable {
    private int gameId;
    public GameStatus[] player = new GameStatus[2];
    public boolean isready;
    public int[] readyarr;
    public int roomsize;

    public Room() {
<<<<<<< HEAD
=======
        roomsize = 1;
>>>>>>> b75f259 (9.42)
//        isready = false;
//        readyarr = new int[2];
//        for (int i = 0; i < 2; i++) {
//            readyarr[i] = 0;
//        }
        player = new GameStatus[2];
        isready = false;
    }

    public int getRoomsize(){return roomsize;}

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setOwner(int rid, GameStatus gameStatus) {
        gameId = rid;
        gameStatus.setGameId(rid);
        gameStatus.setSeat(0);
        gameStatus.setInRoom(true);

        player[0] = gameStatus;
    }

    public GameStatus[] getPlayer() {
        return player;
    }
}
