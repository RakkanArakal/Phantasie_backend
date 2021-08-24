package com.phantasie.demo.springboottest;

import com.alibaba.fastjson.JSON;
import com.phantasie.demo.controller.SingleModeController;
import com.phantasie.demo.utils.msg.SingleModeInfo;
import org.junit.jupiter.api.Test;

public class MsgTest {
    SingleModeController instance = new SingleModeController();
    @Test
    public void testStringfy(){
        System.out.println(instance.getData().getStatus());
        SingleModeInfo data = new SingleModeInfo();
        System.out.println(JSON.toJSON(data).toString());
    }
}
