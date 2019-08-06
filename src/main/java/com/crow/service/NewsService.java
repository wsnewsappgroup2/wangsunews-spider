package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crow.dao.NewsListMapper;
import com.crow.entity.NewsList;
import com.crow.entity.NewsDetailCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 王衍庆
 */
@Service
public class NewsService {

    @Autowired
    NewsListMapper newsListMapper;

    /**首页获取新闻推荐列表**/
    public String getNewsList(String label, Integer start, Integer limit){
        List<NewsList> news=newsListMapper.selectNewsListByLabel(label,start,limit);
        return genResponse(news);
    }


    /**根据标题进行模糊查询**/
    public String searchNewsLikeTitle(String title, Integer start, Integer limit) {
        List<NewsList> list=newsListMapper.selectNewsListLikeTitle(title,start,limit);
        return genResponse(list);
    }

    /**根据newsId获取**/
    public String getSingleNewById(String newsId){
        NewsDetailCustom newsVO=newsListMapper.selectNewsDetailById(newsId);

        JSONObject response=new JSONObject();
        if(newsVO!=null){
            response.put("data",newsVO);
            response.put("status","success");
            response.put("code","success");
        }else{
            response.put("data",null);
            response.put("status","fail");
            response.put("code","fail");
        }
        return response.toJSONString();
    }

    /**生成响应的JSON字符串**/
    private String genResponse(List<NewsList> list){
        JSONObject response=new JSONObject();

        if(list.size()>0){
            // 获取成功
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
            response.put("data",array);
            response.put("status","success");
            response.put("code","success");
        }else{
            // 获取失败
            response.put("data",null);
            response.put("status","fail");
            response.put("code","fail");
        }
        return response.toJSONString();
    }
}
