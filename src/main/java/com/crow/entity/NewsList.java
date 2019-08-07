package com.crow.entity;

import java.util.Date;

public class NewsList {
    private int id;
    private String label;
    private String source;
    private String mainImage;
    private String title;
    private Date createDate;
    private Date newsDate;
    private int sourceCommentNum;
    private String topicWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public int getSourceCommentNum() {
        return sourceCommentNum;
    }

    public void setSourceCommentNum(int sourceCommentNum) {
        this.sourceCommentNum = sourceCommentNum;

    }

    public String getTopicWord() {
        return topicWord;
    }

    public void setTopicWord(String topicWord) {
        this.topicWord = topicWord;
    }
}