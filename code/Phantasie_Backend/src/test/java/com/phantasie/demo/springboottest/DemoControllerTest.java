package com.phantasie.demo.springboottest;

import com.phantasie.demo.controller.LoginController;
import com.phantasie.demo.service.UserService;

import com.phantasie.demo.utils.msgutils.MsgCode;
import com.phantasie.demo.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;


@WebMvcTest(LoginController.class)
public class DemoControllerTest {
   @Autowired
   private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void testSignup()throws Exception{
        JSONObject req = new JSONObject();
        req.put("username:","hsl");
        req.put("password","abcde");
        req.put("phone","12345");
        JSONObject obj = new JSONObject();
        obj.put("name","hsl");
        given(this.userService.signup("hsl","abcde","12345"))
                .willReturn(MsgUtil.makeMsg(MsgCode.SIGNUP_SUCCESS,MsgUtil.SIGNUP_SUCCESS_MSG,obj));

        mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/signup")
        .content(req.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().string(obj.toString())).andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
