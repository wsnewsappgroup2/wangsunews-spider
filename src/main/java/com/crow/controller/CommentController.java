package com.crow.controller;


import com.crow.entity.custom.UserCommentCustom;
import com.crow.result.CommonResult;
import com.crow.result.NewsCommentResult;
import com.crow.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangyq1
 * Last Modified By wangyq1 2019.8.10
 */
@RestController
public class CommentController {
    @Autowired
    UserCommentService userCommentService;

    // 评论列表获取
    @GetMapping(value = "/wsnews/news/news_comments/{newsId}")
    public CommonResult<List<NewsCommentResult>> getNewsCommentsByNewsId(
            @PathVariable("newsId") Integer newsId){
        return userCommentService.getLatestCommentsByNewsId(newsId);
    }
}
