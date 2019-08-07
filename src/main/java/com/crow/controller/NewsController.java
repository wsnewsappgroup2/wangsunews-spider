package com.crow.controller;

import com.crow.result.NewsDetailResult;
import com.crow.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 王衍庆
 * Changed by wuy2 2019.8.7
 */
@CrossOrigin
@RestController
public class NewsController {

    @Autowired
    NewsService newsService;

    /**新闻列表**/
    @GetMapping(value = "/wsnews/news_recommend/{columnId}/{start}/{limit}")
    public String getNews(
            @RequestHeader(value="token") String token,
            @PathVariable("columnId") String label,
            @PathVariable("start") Integer start,
            @PathVariable("limit") Integer limit){
        // TODO: 查看权限校验的问题
        return newsService.getNewsList(label,start,limit);
    }

    /**新闻搜索**/
    @GetMapping(value = "/wsnews/news_search/{keyword}/{start}/{limit}")
    public String searchNews(
            @PathVariable("keyword") String keyword,
            @PathVariable("start") Integer start,
            @PathVariable("limit") Integer limit){
        return newsService.searchNewsLikeTitle(keyword,start,limit);
    }

    /**单个新闻信息页**/
    @GetMapping(value = "/wsnews/new_info/{newsId}")
    public NewsDetailResult getSingleNew(@PathVariable String newsId){
        return newsService.getSingleNewById(newsId);
    }
}
