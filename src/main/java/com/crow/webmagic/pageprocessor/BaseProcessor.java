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
    public void setPost(String title, String content,
                         String source, String mainImage,
                         String newsDate, String sourceCommentNum,
                         String topicWord, Page page,String dateFormat) {
        Post post = new Post();
        simplePostWapper(title,content,source,mainImage,newsDate,dateFormat,post);
        extendPostWrapper(sourceCommentNum,topicWord,post);
        page.putField("postInfo", post);
    }
    public void setPost(String title, String content,
                        String source, String mainImage,
                        String newsDate, Page page,String dateFormat) {
        Post post = new Post();
        simplePostWapper(title,content,source,mainImage,newsDate,dateFormat,post);
        page.putField("postInfo", post);
    }
    private void simplePostWapper(String title, String content, String source, String mainImage,
                                  String newsDate,String dateFormat,Post post){

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

    }
    private void extendPostWrapper(String sourceCommentNum, String topicWord,Post post){
        post.setSourceCommentNum(sourceCommentNum);
        post.setTopicWord(topicWord);
    }
}
