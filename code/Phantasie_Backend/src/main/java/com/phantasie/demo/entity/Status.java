package com.phantasie.demo.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Data
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")

    private Integer id;

    private Integer statusId;

    private String statusName;

    private Integer effect_phase;

    private Integer pile;

    private Integer duration;

    private Integer durative;

    private Integer special;

    private Integer special_count;

    private Integer effect_cost;

    private Integer effect_value;
}
