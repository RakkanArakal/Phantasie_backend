package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class SingleModeInfo {
    
    public int skillId;

    public int level;

    public int exp;

    public int gold;

    public int curHp;

    public int maxHp;

    public int maxMp;

    String mapRoute;

    public String curJob;

    public String curBless;

    public String curSkill;

    private List<Integer> curCardDeck = new LinkedList<>();

    private List<Integer> curCardRepo = new LinkedList<>();

}
