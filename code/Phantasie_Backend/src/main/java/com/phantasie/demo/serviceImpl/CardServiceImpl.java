package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.dao.CardDao;
import com.phantasie.demo.entity.Card;
import com.phantasie.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Override
    public List<Card> getAllCard() {

        return cardDao.getAllCards();
    }

}
