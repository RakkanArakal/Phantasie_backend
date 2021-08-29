package com.phantasie.demo.controller;

import com.phantasie.demo.entity.SkillBuff;
import com.phantasie.demo.service.SkillBuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data_base")
public class SkillBuffController {

    @Autowired
    private SkillBuffService skillBuffService;

    public static Map<Integer ,SkillBuff> allSkillBuffs = new ConcurrentHashMap<>();

    @PostConstruct
    private void init(){
        List<SkillBuff> skillBuffs= skillBuffService.getAllSkillBuffs();
        allSkillBuffs = skillBuffs.stream().collect(Collectors.toMap(SkillBuff::getSkillBuff_id, a -> a,(k1, k2)->k1));
//        System.out.print(allSkillBuffs);
        return;
    }
    @RequestMapping("/getSkill")
    public List<SkillBuff> getDatabaseSkill(){
        return skillBuffService.getAllSkillBuffs();
    }
}
