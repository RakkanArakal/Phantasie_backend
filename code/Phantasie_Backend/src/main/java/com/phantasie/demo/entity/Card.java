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
    private int card_id;

    private String name;

    private int level;

    private int apCost;


}
