package com.crow.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Mapper
public interface UserClicksMapper {

    @Insert("INSERT INTO `user_clicks`(`user_id`,`news_id`,`start_time`,`label`) " +
            "VALUES(#{userId},#{newsId},#{startTime},#{label})")
    void inserClicksRecord(
            @Param("userId") Integer userId,
            @Param("newsId")Integer newsId,
            @Param("startTime")Date startTime,
            @Param("label")String label);

    @Select("SELECT COUNT(*) FROM `user_clicks` WHERE news_id=#{newsId}")
    Integer selectCliclkedCountByNewsId(Integer newsId);
}
