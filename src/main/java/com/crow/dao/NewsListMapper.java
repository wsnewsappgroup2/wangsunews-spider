package com.crow.dao;

import com.crow.entity.NewsList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NewsListMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate})")
    void insert(NewsList newsList);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into news_list (`label`,`source`,`main_image`,`title`,`create_date`,`source_comment_num`,`topic_word`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate}),#{sourceCommentNum}),#{topicWord})")
    void insertSinaEnt(NewsList newsList);

    @Select("SELECT * FROM news_list " +
            "WHERE label=#{label} " +
            "ORDER BY news_date DESC " +
            "LIMIT #{start},#{limit}")
    List<NewsList> getNewsByLabel(
            @Param("label") String label,
            @Param("start") Integer start,
            @Param("limit")Integer limit);
}
