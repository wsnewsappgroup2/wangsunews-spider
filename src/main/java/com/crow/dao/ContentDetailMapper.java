package com.crow.dao;

import com.crow.entity.ContentDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ContentDetailMapper {
    @Insert("insert into content_detail (`news_id`,`content`,`content_type`,`index_id`) values(#{newsId},#{content},#{contentType},#{indexId})")
    void insert(ContentDetail contentDetail);

    @Select("SELECT * FROM `content_detail` WHERE `news_id`=#{newsId}")
    @Results(value = {
            @Result(property = "newsId",column = "news_id"),
            @Result(property = "contentType",column = "content_type"),
            @Result(property = "indexId",column = "index_id")
    })
    List<ContentDetail> selectContentDetailsByNewsId(@Param("newsId") Integer newsId);
}
