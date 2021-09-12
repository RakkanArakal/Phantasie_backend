package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.dao.SingleModeDao;
import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.service.SingleModeService;
import com.phantasie.demo.utils.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleModeServiceImpl implements SingleModeService {

    @Autowired
    private SingleModeDao singleModeDao;


    @Override
    public List<SingleMode> getAllSingleMode() {
        return singleModeDao.getAllSingleMode();
    }

    @Override
    public Msg newGame(String job, int userId) {
        return singleModeDao.cerateSingleMode(job,userId);
    }

    @Override
    public void save(SingleMode singleMode) {
        singleModeDao.save(singleMode);
        return ;
    }
}
