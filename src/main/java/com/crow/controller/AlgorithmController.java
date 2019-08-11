package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.entity.Algorithm;
import com.crow.result.ColumnedAlgorithmResult;
import com.crow.result.CommonResult;
import com.crow.service.AlgorithmService;
import com.crow.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;


    // 查询栏目下算法列表
    @GetMapping(value ="/wsnews/algorithm/query_column_algo/{columnId}")
    public CommonResult<List<ColumnedAlgorithmResult>> getAlgorithmsUsedInColumn(
            @RequestHeader(value = "Authorization",required = false) String token,
            @PathVariable(value = "columnId",required = false) Integer columnId){
        return algorithmService.getAlgorithmsUsedInColumn(token,columnId);
    }


    // 应用或取消栏目下算法
    @PostMapping(value = "/wsnews/algorithm/apply_algo",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String applyOrCancelAlgortihmUsageStatus(
            @RequestHeader(value = "Authorization",required = false) String token,
            @RequestBody(required = false) Map<String,Object> map){
        Integer algorithmId=(Integer)map.get("algorithmId");
        Boolean hasApply=(Boolean)map.get("hasApply");
        Integer columnId=(Integer)map.get("columnId");

        String openId= JwtUtil.getOpenid(token);
        if(openId!=null && columnId!=null){
            algorithmService.updateUserLabelAlgorithm(openId,columnId,algorithmId,hasApply);
            JSONObject response=new JSONObject();
            response.put("success","success");
            response.put("msg","success");
            return response.toJSONString();
        }else{
            JSONObject response=new JSONObject();
            response.put("success","fail");
            response.put("msg","fail");
            return response.toJSONString();
        }
    }
}
