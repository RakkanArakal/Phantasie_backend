package com.phantasie.demo.controller;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Room implements Serializable {
    private int gameId;
    public GameStatus[] player;
    public boolean isready;
    public int[] readyarr;
    public int roomsize;

    public Room() {
        isready = false;
        roomsize = 0;
        readyarr = new int[2];
        for (int i = 0; i < 2; i++) {
            readyarr[i] = 0;
        }
        player = new GameStatus[2];
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
