package com.crow.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserCollectionMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `user_collection`(`user_id`,`news_id`) VALUES(#{userId},#{newsId}) ")
    void insertNewCollectionById(
            @Param("userId")Integer userId,
            @Param("newsId")Integer newsId);


    @Delete("DELETE FROM `user_collection` WHERE `user_id`=#{userId} AND `news_id`=#{newsId}")
    void deleteNewsCollectionById(
            @Param("userId")Integer userId,
            @Param("newsId")Integer newsId);

    @Select("SELECT COUNT(*) FROM `user_collection` WHERE `user_id`=#{userId} AND `news_id`=#{newsId}")
    Integer selectIsCollected(@Param("userId")Integer userId,@Param("newsId")Integer newsId);
}
