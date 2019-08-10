package com.crow.controller;

/**
 * Created by wangyq1
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class NewsControllerTest {
/*    private MockMvc mockMvc;

    @Autowired
    NewsController newsController;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(newsController).build();
    }

    *//**测试获取推荐的新闻列表功能**//*
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
    }*/
}
