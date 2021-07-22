package com.phantasie.demo.daoImpl;

import com.phantasie.demo.dao.CardDao;
import com.phantasie.demo.entity.Card;
import com.phantasie.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CardDaoImpl implements CardDao {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAllCards() {

        return cardRepository.findAll();
    }

}
