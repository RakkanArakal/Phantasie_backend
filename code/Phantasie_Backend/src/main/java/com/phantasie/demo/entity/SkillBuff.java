package com.phantasie.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "skillBuff")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "skillBuff")
public class SkillBuff {
    @Id
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private Integer skillBuff_id;

    private String skillBuff_name;

    private Integer type;

    private Integer attribute;

    private Integer emy_hp;

    private Integer my_hp;

    private Integer emy_cost;

    private Integer my_cost;

    private Integer emy_status;

    private Integer my_status;

    private Integer duration;

    private int special;

    private int special_count;

}
