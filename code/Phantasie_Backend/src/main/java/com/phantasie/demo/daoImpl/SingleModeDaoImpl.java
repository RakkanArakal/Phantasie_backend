package com.phantasie.demo.daoImpl;

import com.phantasie.demo.dao.SingleModeDao;
import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.repository.SingleModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SingleModeDaoImpl implements SingleModeDao {

    @Autowired
    private SingleModeRepository singleModeRepository;


    @Override
    public List<SingleMode> getAllSingleMode() {

        return singleModeRepository.findAll();
    }

    @Override
    public void cerateSingleMode(String job, int userId) {
        SingleMode singleMode = new SingleMode(job,userId);
        singleModeRepository.saveAndFlush(singleMode);
    }

}
