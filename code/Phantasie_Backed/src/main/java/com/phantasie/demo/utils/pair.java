package com.phantasie.demo.utils;

import java.io.Serializable;

public class pair implements Serializable {
    private int x;
    private int y;

    public pair() {}
    public pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof pair))
            return false;
        pair O = (pair) o;
        return x == O.x && y == O.y;
    }
}