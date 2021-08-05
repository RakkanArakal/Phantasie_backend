package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class newState {
    private int seat;
    private int varType;
    private boolean plusVar;
    private int changeVar;
    private int updateVar;
    private int special = 0;
    private List<Integer> useableCard = null;

    public newState(int s, int v, boolean p, int c, int u) {
        seat = s;
        varType = v;
        plusVar = p;
        changeVar = c;
        updateVar = u;
        special = 0;

        if(changeVar < 0) {
            changeVar = -1 * c;
            if(plusVar = true) plusVar = false;
            else plusVar = true;
        }

    }
    public newState(int s){
        special = s;
    }
}
