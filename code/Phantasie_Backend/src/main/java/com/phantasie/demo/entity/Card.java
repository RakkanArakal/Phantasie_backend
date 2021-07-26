package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "card")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cartList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardId")
public class Card {

    @Id
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")

    private int card_id;

    @JsonIgnore
    private String card_name;
    @JsonIgnore
    private int type;
    @JsonIgnore
    private int attribute;
    @JsonIgnore
    private int emy_hp;
    @JsonIgnore
    private int my_hp;
    @JsonIgnore
    private int emy_cost;
    @JsonIgnore
    private int my_cost;
    @JsonIgnore
    private int emy_status;
    @JsonIgnore
    private int my_status;
    @JsonIgnore
    private int duration;
    @JsonIgnore
    private int special;
    @JsonIgnore
    private int special_count;

}
