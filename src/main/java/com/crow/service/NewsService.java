package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crow.dao.NewsListMapper;
import com.crow.entity.NewsList;
import com.crow.entity.custom.NewsDetailCustom;
import com.crow.result.NewsContent;
import com.crow.result.NewsDetailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public NewsDetailResult getSingleNewById(String newsId){
        List<NewsDetailCustom> newsDetailCustoms=newsListMapper.selectNewsDetailById(newsId);
        // TODO: 转换成DetailResult

        String label=newsDetailCustoms.get(0).getLabel();
        String title=newsDetailCustoms.get(0).getTitle();
        String source=newsDetailCustoms.get(0).getSource();
        Date newsDate=newsDetailCustoms.get(0).getNewsDate();
        String mainImage=newsDetailCustoms.get(0).getMainImage();

        NewsDetailResult result=new NewsDetailResult();
        result.setLabel(label);
        result.setTitle(title);
        result.setSource(source);
        result.setNewsDate(newsDate);
        result.setMainImage(mainImage);
        List<NewsContent> contentList=new ArrayList<NewsContent>();
        for(NewsDetailCustom custom:newsDetailCustoms){
            NewsContent content=new NewsContent();
            content.setContent(custom.getContent());
            content.setContentType(custom.getContentType());
            contentList.add(content);
        }

        return result;

/*        JSONObject response=new JSONObject();
        if(newsDetailCustoms!=null){
            response.put("data",newsDetailCustoms);
            response.put("status","success");
            response.put("code","success");
        }else{
            response.put("data",null);
            response.put("status","fail");
            response.put("code","fail");
        }
        return response.toJSONString();*/
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
