package com.crow.dao;

import com.crow.entity.NewsList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface NewsListMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate})")
    void insert(NewsList newsList);
}
