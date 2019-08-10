package com.crow.service;

import com.alibaba.fastjson.JSONObject;
import com.crow.dao.UserCommentMapper;
import com.crow.entity.custom.UserCommentCustom;
import com.crow.result.CommonResult;
import com.crow.result.NewsCommentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCommentService {
    @Autowired
    UserCommentMapper userCommentMapper;

    /**根据新闻ID获取最近的新闻评论列表默认最多取100条**/
    public CommonResult<List<NewsCommentResult>> getLatestCommentsByNewsId (Integer newsId){
        // 根据新闻ID获取相关的评论列表
        return getLatestCommentsByNewsId(newsId,0,100);
    }
    public CommonResult<List<NewsCommentResult>> getLatestCommentsByNewsId (Integer newsId,Integer start,Integer pageSize){
        if(start==null || pageSize==null ||start<0 || pageSize >100){
            start=0;
            pageSize=100;
        }

        // 根据新闻ID获取相关的评论列表
        List<UserCommentCustom> userComments=userCommentMapper.selectUserCommentByNewsId(newsId,start,pageSize);
        List<NewsCommentResult> newsCommentResults=userCommentCustoms2NewsCommentResult(userComments);

        // 封装并返回
        CommonResult<List<NewsCommentResult>> commonResult=new CommonResult<List<NewsCommentResult>>();
        commonResult.setData(newsCommentResults);
        commonResult.setSuccess(newsCommentResults!=null);
        if(newsCommentResults!=null){
            commonResult.setMsg("成功获取用户评论列表");
        }else{
            commonResult.setMsg("获取用户评论列表失败");
        }

        return commonResult;
    }

    private List<NewsCommentResult>  userCommentCustoms2NewsCommentResult(List<UserCommentCustom> userComments){
        List<NewsCommentResult> results=null;
        if(userComments==null || userComments.isEmpty()){
            return results;
        }

        results=new ArrayList<NewsCommentResult>();
        for(UserCommentCustom commentCustom:userComments){
            NewsCommentResult commentResult= JSONObject.parseObject(JSONObject.toJSONString(commentCustom),NewsCommentResult.class);
            results.add(commentResult);
        }

        return results;
    }
}
