package com.crow.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserThumbsUpMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `user_thumbsup`(`user_id`,`news_id`) VALUES(#{userId},#{newsId}) ")
    void insertNewThumbsUpById(
            @Param("userId")Integer userId,
            @Param("newsId")Integer newsId);


    @Delete("DELETE FROM `user_thumbsup` WHERE `user_id`=#{userId} AND `news_id`=#{#newsId}")
    void deleteNewsThumbsUpById(
            @Param("userId")Integer userId,
            @Param("newsId")Integer newsId);

    @Select("SELECT COUNT(*) FROM `user_thumbsup` WHERE `user_id`=#{userId} AND `news_id`=#{#newsId}")
    Integer selectIsThumbsUp(@Param("userId")Integer userId, @Param("newsId")Integer newsId);
}
