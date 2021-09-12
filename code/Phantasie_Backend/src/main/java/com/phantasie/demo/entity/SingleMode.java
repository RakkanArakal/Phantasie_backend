package com.phantasie.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.phantasie.demo.utils.msg.jobInfo;
import lombok.Data;
import net.sf.json.JSONObject;

import javax.persistence.*;

@Data
@Entity
@Table(name = "singleMode")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cardList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class SingleMode {
    @Id
    private int user_id;

<<<<<<< HEAD
=======
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
>>>>>>> ebffa55 (1648)
    private User user;

    private int level;

    private int exp;

    private int curHp;

    private int gold;

    private int maxHp;

    private int maxMp;

    private String mapRoute;

    public Integer jobIndex;

    public String jsonArray;

    public String jobInfo;

    public SingleMode(String job, int userId) {
        this.user_id = userId;
        this.level = 1;
        this.exp = 100;
        this.curHp = 2000;
        this.gold = 100 ;
        this.maxHp = 2000;
        this.maxMp = 100;
        this.jobIndex = Integer.valueOf(job);
        this.mapRoute = "";

        jobInfo myJob = new jobInfo(jobIndex);
        this.jsonArray = JSONObject.fromObject(myJob).toString();

        if(this.jobIndex == 0)
            this.maxMp = 5;
    }

    public SingleMode() {

    }

<<<<<<< HEAD
//    TODO:为每个状态设计实体
//
//    TODO:为剧情进度设计实体

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
=======
//    @OneToMany(mappedBy = "singleMode",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private List<SingleModeCard> cardList;

>>>>>>> e7e033a (1659)
    public User getUser(){
        return user;
    }
}
