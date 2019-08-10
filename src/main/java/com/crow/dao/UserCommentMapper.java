package com.crow.dao;

import com.crow.entity.custom.UserCommentCustom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserCommentMapper {

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
}
