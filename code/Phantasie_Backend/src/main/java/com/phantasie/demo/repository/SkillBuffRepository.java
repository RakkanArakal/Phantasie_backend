package com.phantasie.demo.repository;

import com.phantasie.demo.entity.SkillBuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SkillBuffRepository extends JpaRepository<SkillBuff, Integer>, JpaSpecificationExecutor<SkillBuff> {

}
