package com.phantasie.demo.dao;

import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.utils.msgutils.Msg;

import java.util.List;

public interface SingleModeDao {
    List<SingleMode> getAllSingleMode();

    Msg cerateSingleMode(String job, int userId);

    void save(SingleMode singleMode);
}
