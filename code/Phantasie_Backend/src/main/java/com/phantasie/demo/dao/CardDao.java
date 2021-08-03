package com.phantasie.demo.dao;

import com.phantasie.demo.entity.Card;
import com.phantasie.demo.entity.Status;

import java.util.List;

public interface CardDao {
    List<Card> getAllCards();

    List<Status> getAllStatus();
}
