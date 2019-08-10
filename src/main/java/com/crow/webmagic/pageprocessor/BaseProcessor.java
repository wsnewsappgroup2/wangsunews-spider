package com.crow.webmagic.pageprocessor;

import com.crow.entity.Post;
import us.codecraft.webmagic.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @ClassName:CrowProxyProvider
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 23:30 2019/8/8
 * @Version: V1.0
 */

public class BaseProcessor extends AbstractProcessor {
    /**
     * 将抓取到的主题帖post信息传给pipeline进行后续处理
     * @param title 帖子标题
     * @param page 当前页面对象
     */
    public Post setPost(String title, String content,
                         String source, String mainImage,
                         String newsDate, String sourceCommentNum,
                         String topicWord, Page page,String dateFormat,String label,Post post) {
//        Post post = new Post();
        post =simplePostWapper(title,content,source,mainImage,newsDate,dateFormat,label,post);
        post = extendPostWrapper(sourceCommentNum,topicWord,post);
        return post;
//        page.putField("postInfo", post);
    }
    public Post setPost(String title, String content,
                        String source, String mainImage,
                        String newsDate, Page page,String dateFormat,String label,Post post) {
//        Popost = new Post();
        post=simplePostWapper(title,content,source,mainImage,newsDate,dateFormat,label,post);
        return  post;
    }
    private Post simplePostWapper(String title, String content, String source, String mainImage,
                                  String newsDate,String dateFormat,String label,Post post){

        post.setTitle(title);
        post.setContent(content);
        post.setSource(source);
        post.setMainImage(mainImage);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date newsdate= new Date();
        try {
            newsdate = sdf.parse(newsDate);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        post.setNewsDate(newsdate);
        post.setLabel(label);
        return post;

    }
    private Post extendPostWrapper(String sourceCommentNum, String topicWord,Post post){
        post.setSourceCommentNum(sourceCommentNum);
        post.setTopicWord(topicWord);
        return post;
    }
}
