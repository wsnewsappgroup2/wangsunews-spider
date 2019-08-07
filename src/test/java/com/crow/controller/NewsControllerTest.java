package com.crow.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by wangyq1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsControllerTest {
    private MockMvc mockMvc;

    @Autowired
    NewsController newsController;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(newsController).build();
    }

    /**测试获取推荐的新闻列表功能**/
    @Test
    public void testGetRecommendedNewsList() throws Exception{
        String keyword="sport";
        int start=0;
        int limit=5;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/wsnews/news_recommend/{column}/{page}/{pagesize}","sport",0,5)
                .header("Authorization","mytoken")).andReturn();
        if(mvcResult.getResponse()!=null){
            String responseContent=mvcResult.getResponse().getContentAsString();
            System.out.println(responseContent);
        }
    }
}
