package com.crow.service;

import com.alibaba.fastjson.JSONObject;
import com.crow.dao.*;
import com.crow.entity.ContentDetail;
import com.crow.entity.NewsList;
import com.crow.entity.User;
import com.crow.entity.custom.ColumnInfoCustom;
import com.crow.entity.custom.PersonalColumnInfoCustom;
import com.crow.enums.AlgorithmType;
import com.crow.enums.LabelType;
import com.crow.result.ColumnsInfoResult;
import com.crow.result.CommonResult;
import com.crow.result.NewsDetailResult;
import com.crow.result.NewsListResult;
import com.crow.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyq1
 */
@Service
public class NewsService {

    @Autowired
    NewsListMapper newsListMapper;

    @Autowired
    ContentDetailMapper contentDetailMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecommendService recommendService;

    @Autowired
    UserThumbsUpMapper userThumbsUpMapper;

    @Autowired
    UserCollectionMapper userCollectionMapper;

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
    public CommonResult<List<NewsListResult>> getRecommendedNewsListByColumnId(Integer columnId,String openId){
        // TODO: 添加推荐算法的调用<到时注意是否有多线程异步等待或者等该过慢的问题>
        //先保证能默认调到算法
        // TODO：通过columnId找到label
        //通过openid找到用户id
        List<User> users=userMapper.getUser(openId);
        List<Integer> mockRecomendedNewsIds;
        if(!(users==null || users.isEmpty())) {
            mockRecomendedNewsIds=recommendService.getRecommend(users.get(0).getId(), AlgorithmType.HOT_BASED_RECOMMEND, LabelType.SPORT_COLOUMN,"1");
        }
        else{
            mockRecomendedNewsIds=null;// Todo:改成获取数据库数据
        }

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

    /**根据新闻ID获取新闻的具体内容**/
    public CommonResult<NewsDetailResult> getSingleNewsContentById(Integer newsId,String token){
        CommonResult<NewsDetailResult> commonResult=new CommonResult<>();
        NewsDetailResult newsDetailResult=new NewsDetailResult();
        Integer userId=userMapper.selectUserIdByOpenId(JwtUtil.getOpenid(token));

        // 获取目标新闻的主要信息
        NewsList newsMainInfo=newsListMapper.selectNewsListBynewsId(newsId);
        if(newsMainInfo!=null){
            newsDetailResult=JSONObject.parseObject(JSONObject.toJSONString(newsMainInfo),NewsDetailResult.class);
        }

        // 获取目标新闻的所有新闻片段
        List<ContentDetail> details=contentDetailMapper.selectContentDetailsByNewsId(newsId);
        List<NewsDetailResult.Fragment> contents=new ArrayList<>();
        if(details!=null){
            for(ContentDetail detail:details){
                NewsDetailResult.Fragment fragment=newsDetailResult.new Fragment();
                fragment.setValue(detail.getContent());
                fragment.setType(detail.getContentType()==0?"text":"img");
                contents.add(fragment);
            }
        }
        newsDetailResult.setContents(contents);

        // 使用模糊查询功能获取固定的4篇相关文章的列表没有相关的则返回空
        List<NewsList> newsLists=null;
        if(newsMainInfo!=null){
            newsLists=newsListMapper.selectNewsListWhereTitleLike((String)newsMainInfo.getTitle(),0,4);
            if(newsLists==null || newsLists.isEmpty())
                newsLists=new ArrayList<NewsList>();
        }
        List<NewsListResult> relations=newsLists2NewsListResults(newsLists);
        newsDetailResult.setRelations(relations);

        // 获取新闻的所有主题词(标签)
        String topicWordsString= newsMainInfo==null? null:newsMainInfo.getTopicWord();
        List<String> topicWordsList=new ArrayList<>();
        if(topicWordsString!=null && !topicWordsString.isEmpty()){
            topicWordsList=Arrays.asList(topicWordsString.split(","));
        }
        newsDetailResult.setLabels(topicWordsList);

        // 获取点赞状态和收藏状态
        newsDetailResult.setHasThumbUp(userThumbsUpMapper.selectIsThumbsUp(userId,newsId)>0);
        newsDetailResult.setHasCollect(userCollectionMapper.selectIsCollected(userId,newsId)>0);

        commonResult.setData(newsDetailResult);
        commonResult.setSuccess(true);
        commonResult.setMsg("成功获取新闻详情页");
        return commonResult;
    }


    private List<NewsListResult> newsLists2NewsListResults(List<NewsList> newsLists){
        List<NewsListResult> newsListResults=new ArrayList<NewsListResult>();
        if(newsLists==null || newsLists.isEmpty()){
            return newsListResults;
        }

        for(NewsList news : newsLists){
            NewsListResult result=JSONObject.parseObject(JSONObject.toJSONString(news),NewsListResult.class);
            if(result!=null){
                newsListResults.add(result);
            }
        }

        return newsListResults;
    }
}
