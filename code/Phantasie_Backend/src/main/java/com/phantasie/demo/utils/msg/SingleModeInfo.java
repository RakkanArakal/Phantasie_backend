package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class SingleModeInfo {

    public int skillId;

    public int level = 0;

    public int exp = 0;

    public int gold = 100;

    public int curHp = 0;

    public int maxHp = 100;

    public int maxMp = 0;

    public String mapRoute;

    public String curJob;

    public String curBless;

    public String curSkill;

    List<Integer> cardList = new LinkedList<>();

    private List<Integer> deckList = new LinkedList<>();

    private List<Integer> statusList = new LinkedList<>();

}
