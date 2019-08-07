package com.crow.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by wangyq1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(new LoginController()).build();
    }

    @Test
    public void testLogin() throws Exception {
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.post("/wsnews/login")
                .param("code","XXX","男"));
        System.out.println("xx");
    }
}
