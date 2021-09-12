package com.phantasie.demo.dao;

import com.phantasie.demo.entity.SingleMode;

import java.util.List;

public interface SingleModeDao {
    List<SingleMode> getAllSingleMode();

    void cerateSingleMode(String job, int userId);
}
