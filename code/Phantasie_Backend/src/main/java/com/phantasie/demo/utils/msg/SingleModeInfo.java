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

    public String mapRoute;

    public String curJob;

    public String curBless;

    public String curSkill;

    List<Integer> cardList = new LinkedList<>();

    private List<Integer> deckList = new LinkedList<>();

    private List<Integer> statusList = new LinkedList<>();

}
