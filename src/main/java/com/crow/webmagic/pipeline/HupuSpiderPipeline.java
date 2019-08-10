package com.crow.webmagic.pipeline;

import com.crow.dao.ContentDetailMapper;
import com.crow.dao.NewsListMapper;
import com.crow.entity.*;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;
/**
 * @ClassName:CrowProxyProvider
 * @Author: wuy2
 * @Description: TODO
 * @Date: Created in 16:30 2019/8/8
 * @Version: V1.0
 */
@Component("PostInfoPipeline")
public class HupuSpiderPipeline implements Pipeline{



    @Autowired
    private NewsListMapper newsListMapper;
    @Autowired
    private ContentDetailMapper contentDetailMapper;


    @Override
    public void process(ResultItems resultItems, Task task) {

        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

            if("postInfo".equals(entry.getKey())) {
                Post post = (Post) entry.getValue();
                if(post != null) {
                    NewsList newsList = new NewsList();
                    newsList.setTitle(post.getTitle());
                    newsList.setSource(post.getSource());
                    newsList.setLabel(post.getLabel());
                    newsList.setCreateDate(DateUtil.now());
                    newsList.setNewsDate(post.getNewsDate());
                    newsList.setMainImage(post.getMainImage());
                    newsList.setSourceCommentNum(((int)(1+Math.random()*(10-1+1))));
                    newsList.setTopicWord("新闻");
                    if(post.getTopicWord()!=null){
                        newsList.setTopicWord(post.getTopicWord());
                    }
                    if(!"404 Not Found".equals(newsList.getTitle())){//空指针，还有字符串
                        this.newsListMapper.insertTest(newsList);
                    }
                    ContentDetail contentDetail = new ContentDetail();
                    contentDetail.setNewsId(newsList.getId());
                    contentDetail.setContent(post.getContent());
                    contentDetail.setContentType(0);
                    contentDetail.setIndexId(0);
                    this.contentDetailMapper.insert(contentDetail);
                }
            }
        }

    }
}
