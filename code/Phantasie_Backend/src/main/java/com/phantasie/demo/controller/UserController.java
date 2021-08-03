package com.phantasie.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.phantasie.demo.entity.User;
import com.phantasie.demo.service.UserService;
import com.phantasie.demo.utils.SessionUtil;
import com.phantasie.demo.utils.msg.jobInfo;
import com.phantasie.demo.utils.msgutils.Msg;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RestController
@RequestMapping("/repository")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/changeInfo")
    public Msg changeInfo(@RequestBody String str){
        System.out.println("更新仓库");
        System.out.println(str);
        net.sf.json.JSONObject auth = SessionUtil.getAuth();
        if(auth == null){
            return MsgUtil.makeMsg(-1,"Error");
        }
        User user = userService.findUserByUsername(auth.getString("userName"));

        jobInfo jobInfoClass = JSONObject.parseObject(str,jobInfo.class);
        if(jobInfoClass.getJob() == null) { return MsgUtil.makeMsg(-1,"Error"); }
        List<jobInfo> jobInfoList = com.alibaba.fastjson.JSONArray
                            .parseArray(user.getJobInfo(),jobInfo.class);
        jobInfoList.set(jobInfoClass.getJob(),jobInfoClass);

        String data = JSONArray.fromObject(jobInfoList).toString();
        userService.setJobInfo(data,user);

        return MsgUtil.makeMsg(0,"success");
    }

    @RequestMapping("/changeName")
    public Msg changeName(@RequestBody Map<String,String> map){
        System.out.println("更新仓库");
        System.out.println(map);
        net.sf.json.JSONObject auth = SessionUtil.getAuth();

        if(auth == null){
            return MsgUtil.makeMsg(-1,"Error");
        }
        User user = userService.findUserByUsername(auth.getString("userName"));
        String nickName = map.get("nickName");
        userService.setNickname(nickName,user);

        return MsgUtil.makeMsg(0,"success");
    }

    @PostConstruct
    private void init(){
//        List<Card> cards= cardService.getAllCard();
//        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
//        return;
    }

}
