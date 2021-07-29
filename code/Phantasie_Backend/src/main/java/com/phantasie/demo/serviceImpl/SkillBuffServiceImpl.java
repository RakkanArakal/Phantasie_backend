package com.phantasie.demo.serviceImpl;

import com.phantasie.demo.dao.SkillBuffDao;
import com.phantasie.demo.entity.SkillBuff;
import com.phantasie.demo.service.SkillBuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillBuffServiceImpl implements SkillBuffService {

    @Autowired
    private SkillBuffDao skillBuffDao;

    @Override
    public List<SkillBuff> getAllSkillBuffs() {

        return skillBuffDao.getAllSkillBuffs();
    }

}
