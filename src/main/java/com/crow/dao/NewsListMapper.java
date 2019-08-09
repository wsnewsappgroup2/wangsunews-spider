package com.crow.dao;

import com.crow.entity.NewsList;
import com.crow.entity.custom.ColumnInfoCustom;
import com.crow.entity.custom.NewsDetailCustom;
import com.crow.entity.custom.PersonalColumnInfoCustom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NewsListMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate})")
    void insert(NewsList newsList);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`,`source_comment_num`,`topic_word`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate},#{sourceCommentNum},#{topicWord})")
    void insertSinaEnt(NewsList newsList);
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO news_list (`label`,`source`,`main_image`,`title`,`create_date`,`news_date`,`source_comment_num`) values(#{label},#{source},#{mainImage},#{title},#{createDate},#{newsDate},#{sourceCommentNum})")
    void insertTest(NewsList newsList);


    // 根据openid获取用户的收藏的栏目信息
    @Select("SELECT ucm.`label_id` AS `label_id`,`column_name` "+
            "FROM `user_column_mapping` ucm LEFT JOIN `label_column_mapping` lcm "+
            "ON ucm.label_id=lcm.label_id "+
            "WHERE openid=#{openId}")
    @Results(id = "labelColumnMappingResultsMap", value = {
            @Result(property = "columnId",column = "label_id"),
            @Result(property = "columnName",column = "column_name")})
    List<PersonalColumnInfoCustom> selectPersonalColumnsByOpenId(@Param("openId") String openId);

    // 获取现有的所有栏目的信息
    @Select("SELECT `label_id`,`column_name` " +
            "FROM `label_column_mapping`")
    @Results(id = "labelColumnMappingResultsMap2", value = {
            @Result(property = "columnId",column = "label_id"),
            @Result(property = "columnName",column = "column_name")})
    List<ColumnInfoCustom> selectAllColumns();

    // 根据栏目id获取新闻主要信息列表
    @Select("SELECT * FROM news_list " +
            "WHERE `label`=(SELECT map.`label` FROM `label_column_mapping` map WHERE map.label_id=#{labelId}) " +
            "ORDER BY news_date DESC " +
            "LIMIT #{start},#{pageSize}")
    @Results(id="newsListResultsMap",value={
            @Result(property = "mainImage",column = "main_image"),
            @Result(property = "createDate",column = "create_date"),
            @Result(property = "newsDate",column = "news_date"),
            @Result(property = "sourceCommentNum",column = "source_comment_num"),
            @Result(property = "topicWord",column = "topic_word")})
    List<NewsList> selectPagedNewsListByLabelId(
            @Param("labelId") Integer labelId,
            @Param("start") Integer start,
            @Param("pageSize")Integer pageSize);

    // 根据新闻ID获取新闻信息主要信息列表
    @Select({"<script> ",
                "SELECT * FROM `news_list` ",
                "WHERE news_list.id IN",
                "<foreach collection='newsIds' item='newsId' open='(' separator=',' close=')'> ",
                    "#{newsId}",
                "</foreach>",
            "</script>"
    })
    @ResultMap("newsListResultsMap")
    List<NewsList> selectNewsListByNewsIds(
            @Param("newsIds") List<Integer> newsIds
    );

    // 根据新闻标题和内容或标题进行模糊查询
    @Select("SELECT nl.* " +
            "FROM news_list nl LEFT JOIN content_detail cd " +
            "ON nl.id=cd.news_id " +
            "WHERE nl.title LIKE '%#{keyword}%' OR cd.content LIKE '%#{keyword}%' " +
            "ORDER BY nl.news_date DESC " +
            "LIMIT #{start},#{limit}")
    @ResultMap("newsListResultsMap")
    List<NewsList> selectNewsListWhereTitleOrContentLike(
            @Param("keyword") String keyword,
            @Param("start") Integer start,
            @Param("limit")Integer limit);











    // 通过新闻ID 获取新闻的详细信息
    @Select("SELECT `label`,`title`,`news_id` AS newsId, `source`, `news_date` AS newsDate,`content`,`main_image` AS mainImage, `content_type` AS contentType " +
            "FROM `content_detail` LEFT JOIN `news_list` ON `content_detail`.news_id=`news_list`.id " +
            "WHERE `news_id`=#{newsId} " +
            "ORDER BY `index_id` ASC")
    List<NewsDetailCustom> selectNewsDetailByNewsId(@Param("newsId") String newsId);
    @Select("SELECT MAX(`id`) FROM news_list")
    Integer selectMaxId();
}