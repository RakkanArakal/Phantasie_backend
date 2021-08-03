package com.phantasie.demo.daoImpl;

import com.phantasie.demo.dao.CardDao;
import com.phantasie.demo.entity.Card;
import com.phantasie.demo.entity.Status;
import com.phantasie.demo.repository.CardRepository;
import com.phantasie.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardDaoImpl implements CardDao {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Card> getAllCards() {

        return cardRepository.findAll();
    }

    @Override
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

}
