package com.phantasie.demo.daoImpl;

import com.phantasie.demo.dao.SingleModeDao;
import com.phantasie.demo.entity.SingleMode;
import com.phantasie.demo.repository.SingleModeRepository;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
    public Msg cerateSingleMode(String job, int userId) {
        SingleMode singleMode = new SingleMode(job,userId);
        singleModeRepository.saveAndFlush(singleMode);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"jsonArray","user"});
        JSONObject obj = JSONObject.fromObject(singleMode,jsonConfig);

        String jobInfoStr = singleMode.getJsonArray();
        jobInfo myjobInfo = com.alibaba.fastjson.JSONArray.parseObject(jobInfoStr,jobInfo.class);

        return MsgUtil.makeMsg(0,"success",obj, JSONArray.fromObject(myjobInfo));
    }

}
