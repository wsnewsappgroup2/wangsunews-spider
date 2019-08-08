package com.crow.controller;

import com.crow.result.CommonResult;
import com.crow.result.NewsDetailResult;
import com.crow.result.NewsListResult;
import com.crow.result.ColumnsInfoResult;
import com.crow.service.NewsService;
import com.crow.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangyq1
 * Last Modified By wangyq1 2019.8.8
 */
@CrossOrigin
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**查询用户个人栏目列表**/
    @GetMapping(value = "/wsnews/query_private_column")
    public ColumnsInfoResult getPersonalColumn(
            @RequestHeader(value = "Authorization",required = false) String token){
        String openid= JwtUtil.getOpenid(token);
        return newsService.getPersonalColums(openid);
    }

    /**获取所有的栏目列表**/
    @GetMapping(value = "/wsnews/query_column")
    public ColumnsInfoResult getAllColumns(
            @RequestHeader(value = "Authorization",required = false) String token){
        return newsService.getAllColumns();
    }

    /**推荐新闻列表**/
    @GetMapping(value = "/wsnews/news_recommend/{columnId}/{page}/{pagesize}")
    public CommonResult<List<NewsListResult>> getRecommendedNewsList(
            @RequestHeader(value = "Authorization",required = false) String token,
            @PathVariable(value = "columnId",required = false) Integer columnId,
            @PathVariable(value = "page",required = false) Integer page,
            @PathVariable(value = "pagesize",required = false) Integer pageSize){
        // TODO: 进阶：使用token获取openid进而查询相关的表进行推荐
        return newsService.getNewsListByCloumn(columnId,page,pageSize);
    }

    /**新闻搜索**/
    @GetMapping(value = "/wsnews/news_search/{keyword}/{page}/{pagesize}")
    public String searchNews(
            @PathVariable("keyword") String keyword,
            @PathVariable("page") Integer start,
            @PathVariable("pagesize") Integer limit){
        return newsService.searchNewsLikeTitle(keyword,start,limit);
    }

    /**单个新闻信息页**/
    @GetMapping(value = "/wsnews/new_info/{newsId}")
    public NewsDetailResult getSingleNew(@PathVariable String newsId){
        return newsService.getSingleNewById(newsId);
    }
}
