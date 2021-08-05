package com.phantasie.demo.springboottest;

import com.phantasie.demo.controller.Game;
import com.phantasie.demo.controller.GameStatus;
import com.phantasie.demo.entity.Card;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class GameStatusTest {
    @Test
    public void TestHpChange(){
        GameStatus gameStatus = new GameStatus();
        gameStatus.newGame();
        gameStatus.hpChange(100);
        Assert.assertEquals(1900,gameStatus.getHp());
        gameStatus.hpChange(-100);
        Assert.assertEquals(2000,gameStatus.getHp());
    }

    @Test
    public void TestApChange(){
        GameStatus gameStatus = new GameStatus();
        gameStatus.newGame();
        gameStatus.apChange(10);
        Assert.assertEquals(90,gameStatus.getAp());
        gameStatus.apChange(-10);
        Assert.assertEquals(100,gameStatus.getAp());
    }


}


class GameAndCardTest{
    Game game;
    GameStatus p1,p2;

    private final Method methods[] = Game.class.getDeclaredMethods();


    Card testCard_1 = new Card();

    private void reset(){
        game = new Game();
        p1 = new GameStatus();
        p2 = new GameStatus();
        p1.newGame();
        p2.newGame();
        game.nowStatus = p1;
        game.enemyStatus = p2;

        testCard_1.setEmy_hp(100);
        testCard_1.setMy_cost(10);
        testCard_1.setMy_hp(0);
        testCard_1.setEmy_cost(0);
    }


    @Test
    public void TestGame(){
        reset();
        //game.normalCard()
        game.normalCard(testCard_1);
        Assert.assertEquals(1900,p2.getHp());
        Assert.assertEquals(90,p1.getAp());
        game.healCard(testCard_1);
        Assert.assertEquals(80,p1.getAp());
        Assert.assertEquals(2000,p2.getHp());
    }
}