package com.crow.dao;

import com.crow.entity.NewsList;
import com.crow.entity.custom.NewsDetailCustom;
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
    @Insert("insert into news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`，`source_comment_num`,`topic_word`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate}),#{sourceCommentNum}),#{topicWord})")
    void insertSinaEnt(NewsList newsList);
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`,`source_comment_num`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate},#{sourceCommentNum})")
    void insertTest(NewsList newsList);


    // 根据栏目id获取新闻主要信息列表
    @Select("SELECT * FROM news_list " +
            "WHERE `label`=(SELECT map.`label` FROM `label_column_mapping` map WHERE map.label_id=#{labelId})" +
            "ORDER BY news_date DESC" +
            "LIMIT #{start},#{pageSize}")
    @Results(id="newsListResultsMap",value={
            @Result(property = "mainImage",column = "main_image"),
            @Result(property = "createDate",column = "create_date"),
            @Result(property = "newsDate",column = "news_date"),
            @Result(property = "sourceCommentNum",column = "source_comment_num"),
            @Result(property = "topicWord",column = "topic_word"),
    })
    List<NewsList> selectPagedNewsListByLabelId(
            @Param("labelId") Integer labelId,
            @Param("start") Integer start,
            @Param("pageSize")Integer pageSize);


    // 获取默认的新闻列表结果
    @Select("SELECT * FROM news_list " +
            "ORDER BY news_date DESC " +
            "LIMIT #{start},#{pageSize}")
    List<NewsList> selectDefaultPagedNewsList(
            @Param("start") Integer start,
            @Param("pageSize") Integer pageSize);

    @Select("SELECT * FROM news_list " +
            "WHERE title LIKE %#{title}% " +
            "LIMIT #{start},#{limit}")
    @ResultMap("newsListResultsMap")
    List<NewsList> selectNewsListWhereTitleOrContentLike(
            @Param("title") String title,
            @Param("start") Integer start,
            @Param("limit")Integer limit);

    @Select("SELECT `label`,`title`,`news_id` AS newsId, `source`, `news_date` AS newsDate,`content`,`main_image` AS mainImage, `content_type` AS contentType" +
            "FROM `content_detail` LEFT JOIN `news_list` ON `content_detail`.news_id=`news_list`.id " +
            "WHERE `news_id`=#{newsId} " +
            "ORDER BY `index_id` ASC")
    List<NewsDetailCustom> selectNewsDetailById(@Param("newsId") String newsId);
}