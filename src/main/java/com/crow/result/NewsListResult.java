package com.crow.result;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Create_by wangyq1
 */
public class NewsListResult {
    // 新闻标题
    private String title;
    // 新闻来源
    private String source;
    // 新闻评论数
    private Integer commentCount;
    // 新闻时间（格式：2016-06-07 20:23:59）
    private String timestamp;
    // 新闻主图连接
    private String mainImage;
    // 新闻id
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
