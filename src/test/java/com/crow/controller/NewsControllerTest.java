package com.crow.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(new NewsController()).build();
    }

    @Test
    public void testTokenFilter() throws Exception{
        String keyword="sport";
        int start=0;
        int limit=5;
        ResultActions resultActions= mockMvc.perform(MockMvcRequestBuilders
                .get("/wsnews/news_recommend/{keyword}/{start}/{limit}",keyword,start,limit)
                .header("Authorization","mytoken"));
    }
}
