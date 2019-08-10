package com.crow.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created By wangyq1
 * 用于用户个人相关的响应控制
 * */
@RestController
public class UserActionController {
    // 新增个人栏目
    //@PutMapping("/wsnews/column/add_column")
    public void addPersonalColumn(@RequestBody Map<String,String> map){}
    // 删除个人栏目
    //@DeleteMapping("/wsnews/column/delete_column")
    public void deletePersonalColumn(){}
    // 获取栏目下应用算法
    public void getAppliedAlgorithmInColumn(){}


    /**添加用户个人评论**/
/*    @PostMapping(value = "/wsnews/news/news_add_comment",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String addUserComment(
            @RequestHeader(value = "Authorization",required = false) String token,
            @RequestBody(required=false) Map<String,Object> map){
    }*/


/*    *//**更新单个新闻列表的收藏状态**//*
    @PostMapping(value = "/wsnews/news/news_favor",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String updateNewsCollectedStatus(
            @RequestHeader(value = "Authorization",required = false) String token,
            @RequestBody(required=false) Map<String,Object> map){
        Integer newsId=(Integer)map.get("newsId");
        Boolean hasCollect=(Boolean)map.get("hasCollect");
        return "OK";
    }*/

/*    *//**更新单个新闻列表的点赞状态**//*
    @PostMapping(value = "/wsnews/news/news_praise",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String updateNewsThumbsUpedStatus(
            @RequestHeader(value = "Authorization",required = false) String token,
            @RequestBody(required=false) Map<String,Object> map){
        Integer newsId=(Integer)map.get("newsId");
        Boolean hasThumbUp=(Boolean)map.get("hasThumbUp");
        return "OK";
    }*/


}
