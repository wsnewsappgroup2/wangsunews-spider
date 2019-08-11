package com.crow.dao;

import com.crow.entity.custom.UserCommentCustom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface UserCommentMapper {

    // 根据新闻ID获取目标新闻的所有相关评论
    @Select("SELECT `user_comment`.*,`user`.`avatar_url` AS `avatar_url` " +
            "FROM `user_comment` LEFT JOIN `user` ON `user`.id=`user_comment`.user_id " +
            "WHERE `news_id`=#{newsId} " +
            "ORDER BY `created_date` DESC " +
            "LIMIT #{start},#{pageSize}")
    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "newsId",column = "news_id"),
            @Result(property = "nickName",column = "nick_name"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "avatarUrl",column = "avatar_url")
    })
    List<UserCommentCustom> selectUserCommentByNewsId(
            @Param("newsId") Integer newsId,
            @Param("start") Integer start,
            @Param("pageSize") Integer pageSize);

    // 添加新的评论
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("INSERT INTO `user_comment`(`user_id`,`news_id`,`nick_name`,`comment`,`created_date`) " +
            "VALUES(#{userId},#{newsId},#{nickName},#{comment},#{createdDate})")
    void insertNewUserComment(
            @Param("userId") Integer userId,
            @Param("newsId") Integer newsId,
            @Param("nickName") String nickName,
            @Param("comment") String comment,
            @Param("createdDate")Date createdDate);
}
