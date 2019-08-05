package com.crow.entity;

public class CommentDetail {
    private int id;
    private int newsId;
    private String author;
    private String comment;
    private int IndexId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIndexId() {
        return IndexId;
    }

    public void setIndexId(int indexId) {
        IndexId = indexId;
    }
}
