package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public CommonResult<List<NewsListResult>> getNewsListByCloumn(Integer columnId, Integer page, Integer pageSize){
        List<NewsList> newsList=null;

        int start=page*pageSize;

        if(columnId!=-1){
            // 具体栏目列表
            newsList=newsListMapper.selectPagedNewsListByLabelId(columnId,start,pageSize);
        }else{
            // 默认推荐列表
            newsList=newsListMapper.selectDefaultPagedNewsList(start,pageSize);
        }

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
