package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class newState {
    private int seat;
    private int varType;
    private boolean plusVar;
    private int changeVar;
    private int updateVar;

    public newState(int s, int v, boolean p, int c, int u) {
        seat = s;
        varType = v;
        plusVar = p;
        changeVar = c;
        updateVar = u;
    }
}
