package com.phantasie.demo.service;

import com.phantasie.demo.entity.Card;
import com.phantasie.demo.entity.Status;

import java.util.List;

public interface CardService {
    List<Card> getAllCard();
    List<Status> getAllStatus();
}
