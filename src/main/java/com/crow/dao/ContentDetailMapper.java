package com.crow.dao;

import com.crow.entity.ContentDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentDetailMapper {
    @Insert("insert into content_detail (`news_id`,`content`,`content_type`,`index_id`) values(#{newsId},#{content},#{contentType},#{indexId})")
    void insert(ContentDetail contentDetail);
}
