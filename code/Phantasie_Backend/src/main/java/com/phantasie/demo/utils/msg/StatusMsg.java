package com.phantasie.demo.utils.msg;

import com.phantasie.demo.entity.Status;
import lombok.Getter;
import lombok.Setter;

import static com.phantasie.demo.controller.CardController.allStatus;

@Setter
@Getter
public class StatusMsg {

    private Integer statusId;

    private Integer effect_phase;

    private Integer duration;

    private Integer durative;

//    private Integer special;
//
//    private Integer special_count;

    private Integer effect_value;

    private Integer effect_cost;



    public StatusMsg(int statusId) {
        Status status = allStatus.get(statusId);

        this.statusId = status.getStatusId() ;

        this.effect_phase = status.getEffect_phase();

        this.duration = status.getDuration();

        this.durative = status.getDurative();

        this.effect_value = status.getEffect_value();

        this.effect_cost = status.getEffect_cost();
    }
}