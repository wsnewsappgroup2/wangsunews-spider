package com.crow.dao;

import com.crow.entity.NewsList;
import com.crow.entity.NewsDetailCustom;
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
    List<NewsList> selectNewsListByLabel(
            @Param("label") String label,
            @Param("start") Integer start,
            @Param("limit")Integer limit);

    @Select("SELECT * FROM news_list " +
            "WHERE title LIKE %#{title}% " +
            "LIMIT #{start},#{limit}")
    List<NewsList> selectNewsListLikeTitle(
            @Param("title") String title,
            @Param("start") Integer start,
            @Param("limit")Integer limit);

    @Select("SELECT `label`,`title`,`news_id` AS newsId, `source`, `news_date` AS newsDate,`content`,`main_image` AS mainImage, `content_type` AS contentType, `index_id` AS indexId" +
            "FROM `content_detail` LEFT JOIN `news_list` ON `content_detail`.news_id=`news_list`.id " +
            "WHERE `news_id`=#{newsId} " +
            "ORDER BY `index_id` ASC")
    List<NewsDetailCustom> selectNewsDetailById(@Param("newsId") String newsId);
}