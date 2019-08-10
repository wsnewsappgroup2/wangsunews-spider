package com.crow.service;

import org.springframework.stereotype.Service;

@Service
public class UserActionSerivce {

    /**更新单个新闻的收藏状态**/
    public void updateNewsCollectedStatus(Integer newsId,Boolean action){
        if(action){
            // 收藏（新增收藏映射）
        }else{
            // 取消收藏（删除收藏映射）
        }
    }

    /**更新单个新闻的点赞状态**/
    public void updateNewsThumbsUpStatus(Integer newsId,Boolean action){
        if(action){
            // 点赞（新增点赞映射）
        }else{
            // 取消点赞（删除点赞映射）
        }
    }
}
