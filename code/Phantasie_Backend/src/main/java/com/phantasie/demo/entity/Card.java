package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
    @Column(name = "card_Id")

    private String card_name;

    private int card_id;

    private int type;

    private int attribute;

    private int emy_hp;

    private int my_hp;

    private int emy_cost;

    private int my_cost;

    private int emy_status;

    private int my_status;

    private int duration;

    private int special;

    private int special_count;

}
