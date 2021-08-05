package com.phantasie.demo.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Data
@Entity
@Table(name = "card")
//@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cartList", "orderList"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardId")
public class Card implements Serializable {

    @Id
//    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITYunlockCard)
    @GenericGenerator(name = "increment", strategy = "increment")

    private Integer card_id;

    private String card_name;

    private Integer type;

    private Integer job;

    private Integer attribute;

    private Integer emy_hp;

    private Integer my_hp;

    private Integer emy_cost;

    private Integer my_cost;

    private Integer emy_status;

    private Integer my_status;

    private Integer duration;

    private Integer special;

    private Integer special_count;

    private Integer rare;

}
