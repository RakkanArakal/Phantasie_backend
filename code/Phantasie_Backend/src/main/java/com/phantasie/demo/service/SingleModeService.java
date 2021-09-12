package com.phantasie.demo.service;

import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.utils.msgutils.Msg;

import java.util.List;

public interface SingleModeService {

    List<SingleMode> getAllSingleMode();

    Msg newGame(String job, int userId);
}
