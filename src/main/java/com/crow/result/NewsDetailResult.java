package com.crow.result;

import java.util.Date;
import java.util.List;

/**
 * 用于显示单个新闻页面的相关信息
 * **/
public class NewsDetailResult {
    private String label;
    private String title;
    private String newsId;
    private String source;
    private Date newsDate;
    private String mainImage;

    public class NewsContent{
        String content;
        String contentType;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }
    private List<NewsContent> newsContents;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<NewsContent> getNewsContents() {
        return newsContents;
    }

    public void setNewsContents(List<NewsContent> newsContents) {
        this.newsContents = newsContents;
    }
}