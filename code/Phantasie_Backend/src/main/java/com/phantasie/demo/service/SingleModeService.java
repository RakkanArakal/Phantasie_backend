package com.phantasie.demo.service;

import com.phantasie.demo.entity.SingleMode;

import java.util.List;

public interface SingleModeService {

    List<SingleMode> getAllSingleMode();

    void newGame(String job, int userId);
}
