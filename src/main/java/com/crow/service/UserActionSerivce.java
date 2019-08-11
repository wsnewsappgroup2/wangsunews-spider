package com.crow.service;

import com.crow.dao.UserCollectionMapper;
import com.crow.dao.UserCommentMapper;
import com.crow.dao.UserMapper;
import com.crow.dao.UserThumbsUpMapper;
import com.crow.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class UserActionSerivce {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserCollectionMapper collectionMapper;

    @Autowired
    UserThumbsUpMapper thumbsUpMapper;

    @Autowired
    UserCommentMapper userCommentMapper;

    /**添加用户评论**/
    public void addNewUserComment(String openId,String comment,Integer newsId){
        Integer userId=userMapper.selectUserIdByOpenId(openId);
        String nickName=userMapper.selectNickNameByOpenId(openId);
        if(userId==null || StringUtils.isBlank(nickName) || StringUtils.isBlank(comment)){
            return;
        }
        Date date=new Date();
        userCommentMapper.insertNewUserComment(userId,newsId,nickName,comment,date);
    }

    /**更新单个新闻的收藏状态**/
    public void updateNewsCollectedStatus(Integer newsId,Boolean action,String openId){
        Integer userId=userMapper.selectUserIdByOpenId(openId);
        if(userId==null){
            return;
        }
        if(action){
            // 收藏（新增收藏映射）
            collectionMapper.insertNewCollectionById(userId,newsId);
        }else{
            // 取消收藏（删除收藏映射）
            collectionMapper.deleteNewsCollectionById(userId,newsId);
        }
    }

    /**更新单个新闻的点赞状态**/
    public void updateNewsThumbsUpStatus(Integer newsId,Boolean action,String openId){
        Integer userId=userMapper.selectUserIdByOpenId(openId);
        if(userId==null){
            return;
        }
        if(action){
            // 点赞（新增点赞映射）
            thumbsUpMapper.insertNewThumbsUpById(userId,newsId);
        }else{
            // 取消点赞（删除点赞映射）
            thumbsUpMapper.deleteNewsThumbsUpById(userId,newsId);
        }
    }
}
