package com.phantasie.demo.utils.msg;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class jobInfo {

    private Integer job;

    private Integer buffId = 0;

    private Integer skillId = 0;

    private List<Integer> unlockCard = new LinkedList<>();

    private List<Integer> unlockSkill = new LinkedList<>();

    private List<Integer> unlockBuff = new LinkedList<>();

    private List<Integer> cardLibrary = new LinkedList<>();

    public jobInfo (int i){
        job = i ;
        switch (job){
            case 0:{
                unlockSkill= List.of(101);
                unlockBuff = List.of(201,202,203);
                unlockCard = List.of(0,100,500,200,201,202,203,204);
                cardLibrary = List.of(500,100,0,0,0,200,200,201,203,204);

            }break;
            case 1:{
                unlockSkill= List.of(102);
                unlockBuff = List.of(221,222,223,224,225);
                unlockCard = List.of(0,100,500,300,301,302,303,304);
                cardLibrary = List.of(500,100,0,300,301,301,302,303,304,304);

            }break;
            case 2:{
                unlockSkill= List.of(103);
                unlockBuff = List.of(241,242);
                unlockCard = List.of(0,100,500,400,401,402,403,404);
                cardLibrary = List.of(500,100,0,0,400,400,401,402,403,404);
            }break;
        }
    }
    public jobInfo(){}

    public static List<jobInfo> initJob(){
        List<jobInfo> jobInfoList = new LinkedList<>();
        jobInfoList.add(new jobInfo(0));
        jobInfoList.add(new jobInfo(1));
        jobInfoList.add(new jobInfo(2));
        return jobInfoList;
    }
}
