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

import static com.phantasie.demo.controller.LoginController.allJobInfos;
import static com.phantasie.demo.controller.LoginController.allUsers;


@Getter
@Setter
@RestController
@RequestMapping("/repository")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping("/changeInfo")
//    public Msg changeInfo(@RequestBody Map<String,String> map){
//        System.out.println("更新仓库");
//        System.out.println(map);
//        String token = map.get("token");
//        int userId = tokenMap.get(token);
//        jobInfo jobInfoClass = JSONObject.parseObject(map.get("data"),jobInfo.class);
//        int job = jobInfoClass.getJob();
//        net.sf.json.JSONObject auth = SessionUtil.getAuth();
//
//        if(auth == null){
//            return MsgUtil.makeMsg(-1,"Error");
//        }
//        else{
//            auth.get("userId");
//            return MsgUtil.makeMsg(MsgCode.LOGIN_SUCCESS, MsgUtil.LOGIN_SUCCESS_MSG, auth);
//        }
//
//        allJobInfos.get(userId).set(job,jobInfoClass);
//
//
//        return MsgUtil.makeMsg(0,"success");
//    }
@RequestMapping("/changeInfo")
public Msg changeInfo(@RequestBody String str){
    System.out.println("更新仓库");
    System.out.println(str);
    net.sf.json.JSONObject auth = SessionUtil.getAuth();

    if(auth == null){
        return MsgUtil.makeMsg(-1,"Error");
    }
    Integer userId = (Integer) auth.get("userId");
    jobInfo jobInfoClass = JSONObject.parseObject(str,jobInfo.class);
    User user = allUsers.get(userId);
    List<jobInfo> jobInfoList = allJobInfos.get(userId);
    jobInfoList.set(jobInfoClass.getJob(),jobInfoClass);

    String data = JSONArray.fromObject(jobInfoList).toString();
    userService.setJobInfo(data,user);

    return MsgUtil.makeMsg(0,"success");
}

    @PostConstruct
    private void init(){
//        List<Card> cards= cardService.getAllCard();
//        allCards = cards.stream().collect(Collectors.toMap(Card::getCard_id, a -> a,(k1, k2)->k1));
//        return;
    }

}
