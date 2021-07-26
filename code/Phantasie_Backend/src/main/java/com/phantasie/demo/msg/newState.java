package com.phantasie.demo.msg;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class newState {

    int seat;
    int varType;
    boolean plusVar;
    int changeVar;
    int updateVar;

    public newState(int s, int v, boolean p, int c, int u) {
        seat = s;
        varType = v;
        plusVar = p;
        changeVar = c;
        updateVar = u;
    }

    newState() {
    }
}
