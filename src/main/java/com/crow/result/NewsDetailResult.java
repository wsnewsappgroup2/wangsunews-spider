package com.crow.result;

import java.util.List;

/**
 * 用于显示单个新闻页面的相关信息
 * **/
public class NewsDetailResult {
    // 新闻标题
    private String title;

    // 新闻栏目
    private String cloumn;

    // 新闻id
    private String id;

    // 新闻来源
    private String source;
    // 新闻时间
    private String timestamp;
    // 主图url
    private String mainImage;

    // 新闻内容片段列表
    public class Fragment{
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String value;
        private String type;
    }
    private List<Fragment> contents;

    // 新闻标签(即数据库中的主题词)
    private List<String> labels;

    // 相关文章列表
    private List<NewsListResult> relations;

    // 是否已经点赞
    private boolean hasThumbUp;

    // 是否已经收藏
    private boolean hasCollect;

    // 本条新闻的评论数量
    private Integer commentCount;





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCloumn() {
        return cloumn;
    }

    public void setCloumn(String cloumn) {
        this.cloumn = cloumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<Fragment> getContents() {
        return contents;
    }

    public void setContents(List<Fragment> contents) {
        this.contents = contents;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<NewsListResult> getRelations() {
        return relations;
    }

    public void setRelations(List<NewsListResult> relations) {
        this.relations = relations;
    }

    public boolean isHasThumbUp() {
        return hasThumbUp;
    }

    public void setHasThumbUp(boolean hasThumbUp) {
        this.hasThumbUp = hasThumbUp;
    }

    public boolean isHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
