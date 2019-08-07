package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crow.dao.NewsListMapper;
import com.crow.entity.NewsList;
import com.crow.entity.custom.NewsDetailCustom;
import com.crow.result.CommonResult;
import com.crow.result.NewsDetailResult;
import com.crow.result.NewsDetailResult.NewsContent;
import com.crow.result.NewsListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王衍庆
 */
@Service
public class NewsService {

    @Autowired
    NewsListMapper newsListMapper;

    /**根据 栏目 获取推荐新闻列表**/
    public CommonResult<List<NewsListResult>> getNewsListByCloumn(String column, Integer start, Integer limit){
        List<NewsList> newsList=newsListMapper.selectNewsListByLabel(column,start,limit);

        CommonResult<List<NewsListResult>> commonResult=new CommonResult<List<NewsListResult>>();
        if(newsList==null || newsList.size()<=0){
            commonResult.setMsg("无推荐新闻");
            commonResult.setSuccess("fail");
        }

        List<NewsListResult> newsListResults=new ArrayList<NewsListResult>();
        if(newsList!=null){
            for(NewsList news : newsList){
                NewsListResult result=JSONObject.parseObject(JSONObject.toJSONString(news),NewsListResult.class);
                if(result!=null){
                    newsListResults.add(result);
                }
            }
            commonResult.setMsg("成功推荐新闻");
            commonResult.setSuccess("success");
        }
        commonResult.setData(newsListResults);
        return commonResult;
    }

    /**根据标题进行模糊查询**/
    public String searchNewsLikeTitle(String title, Integer start, Integer limit) {
        List<NewsList> list=newsListMapper.selectNewsListWhereTitleOrContentLike(title,start,limit);
        return genResponse(list);
    }

    /**根据newsId获取**/
    public NewsDetailResult getSingleNewById(String newsId){
        List<NewsDetailCustom> newsDetailCustoms=newsListMapper.selectNewsDetailById(newsId);
        // TODO: 转换成DetailResult

        String label=newsDetailCustoms.get(0).getLabel();
        String title=newsDetailCustoms.get(0).getTitle();
        String source=newsDetailCustoms.get(0).getSource();
        Date newsDate=newsDetailCustoms.get(0).getNewsDate();
        String mainImage=newsDetailCustoms.get(0).getMainImage();

        NewsDetailResult result=new NewsDetailResult();
        result.setLabel(label);
        result.setTitle(title);
        result.setSource(source);
        result.setNewsDate(newsDate);
        result.setMainImage(mainImage);
/*        List<NewsContent> contentList=new ArrayList<NewsContent>();
        for(NewsDetailCustom custom:newsDetailCustoms){
            NewsContent content=new NewsContent();
            content.setContent(custom.getContent());
            content.setContentType(custom.getContentType());
            contentList.add(content);
        }*/

        return result;

/*        JSONObject response=new JSONObject();
        if(newsDetailCustoms!=null){
            response.put("data",newsDetailCustoms);
            response.put("status","success");
            response.put("code","success");
        }else{
            response.put("data",null);
            response.put("status","fail");
            response.put("code","fail");
        }
        return response.toJSONString();*/
    }

    /**生成响应的JSON字符串**/
    private String genResponse(List<NewsList> list){
        JSONObject response=new JSONObject();

        if(list.size()>0){
            // 获取成功
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
            response.put("data",array);
            response.put("status","success");
            response.put("code","success");
        }else{
            // 获取失败
            response.put("data",null);
            response.put("status","fail");
            response.put("code","fail");
        }
        return response.toJSONString();
    }
}
