package com.crow.dao;

import com.crow.entity.NewsList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConmmentDetailMapper {
    @Insert("insert into news_list (`news_id`,`author`,`comment`,`index_id`) values(#{newsId},#{author},#{comment},#{IndexId}")
    void insert(NewsList newsList);
}
