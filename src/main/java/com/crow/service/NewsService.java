package com.crow.service;

import com.alibaba.fastjson.JSONObject;
import com.crow.dao.NewsListMapper;
import com.crow.entity.NewsList;
import com.crow.entity.custom.ColumnInfoCustom;
import com.crow.entity.custom.NewsDetailCustom;
import com.crow.entity.custom.PersonalColumnInfoCustom;
import com.crow.result.CommonResult;
import com.crow.result.NewsDetailResult;
import com.crow.result.NewsListResult;
import com.crow.result.ColumnsInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wangyq1
 */
@Service
public class NewsService {

    @Autowired
    NewsListMapper newsListMapper;

    /**获取用户个人收藏的栏目列表**/
    public ColumnsInfoResult getPersonalColums(String openid){
        ColumnsInfoResult<List<PersonalColumnInfoCustom>> columnsInfoResult =new ColumnsInfoResult<List<PersonalColumnInfoCustom>>();
        if(openid==null){
            columnsInfoResult.setMsg("获取用户收藏的栏目失败");
            columnsInfoResult.setSuccess(false);
            columnsInfoResult.setColumns(new ArrayList<>());
            return columnsInfoResult;
        }

        List<PersonalColumnInfoCustom> personalColumnInfoCustoms = newsListMapper.selectPersonalColumnsByOpenId(openid);
        if(personalColumnInfoCustoms ==null || personalColumnInfoCustoms.isEmpty()){
            // 用户没有收藏的栏目
            columnsInfoResult.setMsg("获取用户收藏的栏目失败");
            columnsInfoResult.setSuccess(false);
            columnsInfoResult.setColumns(new ArrayList<>());
        }else{
            // TODO: 在mapper进行关联查询或在这里重新查询获取使用算法的数量
            columnsInfoResult.setMsg("获取成功");
            columnsInfoResult.setSuccess(true);
            columnsInfoResult.setColumns(personalColumnInfoCustoms);
        }
        return columnsInfoResult;
    }

    /**获取所有的栏目列表**/
    public ColumnsInfoResult getAllColumns(){
        List<ColumnInfoCustom> columnInfoCustoms =newsListMapper.selectAllColumns();
        ColumnsInfoResult<List<ColumnInfoCustom>> columnsInfoResult =new ColumnsInfoResult<List<ColumnInfoCustom>>();

        if(columnInfoCustoms !=null && !columnInfoCustoms.isEmpty()){
            columnsInfoResult.setMsg("获取成功");
            columnsInfoResult.setSuccess(true);
            columnsInfoResult.setColumns(columnInfoCustoms);
        }else{
            // 获取失败时返回的是内容为空的列表
            columnsInfoResult.setMsg("获取现有所有栏目失败");
            columnsInfoResult.setSuccess(false);
            columnsInfoResult.setColumns(new ArrayList<>());
        }
        return columnsInfoResult;
    }

    /**获取特定栏目的新闻列表**/
    public CommonResult<List<NewsListResult>> getNewsListByColumn(Integer columnId, Integer page, Integer pageSize){
        List<NewsList> newsLists=null;
        int start= page==null? 0: page*pageSize;
        // 具体栏目列表
        newsLists=newsListMapper.selectPagedNewsListByLabelId(columnId,start,pageSize);

        CommonResult<List<NewsListResult>> commonResult=new CommonResult<List<NewsListResult>>();
        List<NewsListResult> newsListResults=new ArrayList<>();
        if(newsLists==null || newsLists.isEmpty()){
            commonResult.setMsg("无推荐新闻");
            commonResult.setSuccess(false);
        }else{
            newsListResults=newsLists2NewsListResults(newsLists);
            commonResult.setMsg("成功推荐新闻");
            commonResult.setSuccess(true);
        }
        commonResult.setData(newsListResults);

        return commonResult;
    }

    /**根据标题进行模糊查询**/
    public CommonResult<List<NewsListResult>> vagueSearch(String keyword, Integer page, Integer pageSize) {
        int start= page==null? 0 : page*pageSize;
        List<NewsList> newsLists=newsListMapper.selectNewsListWhereTitleOrContentLike(keyword,start,pageSize);

        CommonResult<List<NewsListResult>> commonResult=new CommonResult<List<NewsListResult>>();
        List<NewsListResult> newsListResults=new ArrayList<>();
        if(newsLists==null || newsLists.isEmpty()){
            commonResult.setMsg("没有找到相似内容");
            commonResult.setSuccess(false);
        }else{
            newsListResults=newsLists2NewsListResults(newsLists);
            commonResult.setMsg("成功找到结果");
            commonResult.setSuccess(true);
        }
        commonResult.setData(newsListResults);
        return commonResult;
    }

    /**对特定的栏目执行推荐算法做推荐**/
    public CommonResult<List<NewsListResult>> getRecommendedNewsListByColumnId(Integer columnId){
        // TODO: 添加推荐算法的调用<到时注意是否有多线程异步等待或者等该过慢的问题>
        List<Integer> mockRecomendedNewsIds=new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10}));

        List<NewsList> recommendedNewsList=newsListMapper.selectNewsListByNewsIds(mockRecomendedNewsIds);

        CommonResult<List<NewsListResult>> commonResult=new CommonResult<>();
        List<NewsListResult> newsListResults=new ArrayList<>();
        if(recommendedNewsList==null || recommendedNewsList.isEmpty()){
            commonResult.setMsg("推荐失败");
            commonResult.setSuccess(false);
        }else{
            newsListResults=newsLists2NewsListResults(recommendedNewsList);
            commonResult.setMsg("推荐成功");
            commonResult.setSuccess(true);
        }
        commonResult.setData(newsListResults);

        return commonResult;
    }

    //根据newsId获取
    public NewsDetailResult getSingleNewById(String newsId){
        List<NewsDetailCustom> newsDetailCustoms=newsListMapper.selectNewsDetailByNewsId(newsId);
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


    private List<NewsListResult> newsLists2NewsListResults(List<NewsList> newsLists){
        List<NewsListResult> newsListResults=new ArrayList<NewsListResult>();
        if(newsLists!=null){
            for(NewsList news : newsLists){
                NewsListResult result=JSONObject.parseObject(JSONObject.toJSONString(news),NewsListResult.class);
                if(result!=null){
                    newsListResults.add(result);
                }
            }
        }
        return newsListResults;
    }
}
