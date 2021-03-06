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
    private int attribute;
    private int special;        //特殊值
//    private List<Integer> useableCard = null;

//    private List<Integer> seatStatusOrder0;
//    private List<Integer> seatStatusOrder1;

    private List<StatusMsg> allStatus0;
    private List<StatusMsg> allStatus1;

    public newState(int s, int v, boolean p, int c, int u, int a, List<StatusMsg> statusList0, List<StatusMsg> statusList1) {
        this.seat = s;
        this.varType = v;
        this.plusVar = p;
        this.changeVar = c;
        this.updateVar = u;
        this.attribute = a;
        this.special = 0;
//        this.seatStatusOrder0 = seat0;
//        this.seatStatusOrder1 = seat1;
        this.allStatus0 = statusList0;
        this.allStatus1 = statusList1;

        if(changeVar < 0) {
            changeVar = -1 * c;
            if(plusVar = true) plusVar = false;
            else plusVar = true;
        }
    }

    public newState(int v, List<StatusMsg> statusList0, List<StatusMsg> statusList1) {
        this.seat = 0;
        this.varType = v;
        this.plusVar = true;
        this.changeVar = 0;
        this.updateVar = 0;
        this.special = 0;
//        this.seatStatusOrder0 = seat0;
//        this.seatStatusOrder1 = seat1;
        this.allStatus0 = statusList0;
        this.allStatus1 = statusList1;
    }

    public newState(int s){
        special = s;
    }
}