package com.phantasie.demo.controller;

<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
=======

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
>>>>>>> 18b44a7 (11:29)
public class Room implements Serializable {
    private int roomId;

    public GameStatus[] player = new GameStatus[2];
//    public boolean isready;
//    public int playerNow;
    public int roomsize;
    public String ownerName;

    public Date date;


    public Room() {
<<<<<<< HEAD
=======
        roomsize = 1;
<<<<<<< HEAD
>>>>>>> b75f259 (9.42)
//        isready = false;
//        readyarr = new int[2];
//        for (int i = 0; i < 2; i++) {
//            readyarr[i] = 0;
//        }
=======
>>>>>>> 18b44a7 (11:29)
        player = new GameStatus[2];
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MINUTE,30);
        date = expireDate.getTime();
    }


    public void setOwner(int rid, GameStatus gameStatus) {
        roomId = rid;
        gameStatus.setGameId(rid);
        gameStatus.setSeat(0);
        gameStatus.setInRoom(true);
        player[0] = gameStatus;
        ownerName = gameStatus.getPlayerName();
    }

    public GameStatus[] getPlayer() {
        return player;
    }


    public boolean isExpired(){
        Calendar now = Calendar.getInstance();
        if ( date.before(now.getTime()) ){
            return true;
        }
        return false;
    }
}
