package com.phantasie.demo.daoImpl;

import com.phantasie.demo.dao.SkillBuffDao;
import com.phantasie.demo.entity.SkillBuff;
import com.phantasie.demo.repository.SkillBuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillBuffDaoImpl implements SkillBuffDao {

    @Autowired
    private SkillBuffRepository skillBuffRepository;

    @Override
    public List<SkillBuff> getAllSkillBuffs() {

        return skillBuffRepository.findAll();
    }

}
