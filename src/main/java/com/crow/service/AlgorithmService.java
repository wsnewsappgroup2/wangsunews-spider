package com.crow.service;


import com.alibaba.fastjson.JSONObject;
import com.crow.dao.UserLabelAlgorithmMapper;
import com.crow.dao.UserMapper;
import com.crow.entity.Algorithm;
import com.crow.result.ColumnedAlgorithmResult;
import com.crow.result.CommonResult;
import com.crow.utils.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AlgorithmService {

    @Autowired
    UserLabelAlgorithmMapper userLabelAlgorithmMapper;

    @Autowired
    UserMapper userMapper;

    // 查询栏目下可用算法的列表
    public CommonResult<List<ColumnedAlgorithmResult>> getAlgorithmsUsedInColumn(String token, Integer columnId){
       CommonResult<List<ColumnedAlgorithmResult>> commonResult=new CommonResult<List<ColumnedAlgorithmResult>>();
        List<ColumnedAlgorithmResult> results=new ArrayList<>();

        List<Algorithm> algorithms=userLabelAlgorithmMapper.selectCloumnedList(columnId);
        for(Algorithm algorithm:algorithms){
            ColumnedAlgorithmResult car=JSONObject.parseObject(JSONObject.toJSONString(algorithm),ColumnedAlgorithmResult.class);
            // TODO: 清除硬编码
            car.setUserate("80%");
            car.setApplied(userLabelAlgorithmMapper.selectAlgorithmUsedCount(algorithm.getId())>0);
            car.setTags(Arrays.asList(new String[] {"通用"}));
            results.add(car);
        }

        commonResult.setSuccess(true);
        commonResult.setData(results);
        commonResult.setMsg("获取成功但有可能是空列表");
        return commonResult;
    }

    public void updateUserLabelAlgorithm(String openId, Integer labelId, Integer algorithmId, Boolean hasApply){
        Integer userId= userMapper.selectUserIdByOpenId(openId);
        if(userId==null || labelId==null || algorithmId==null){return;}

        if(hasApply){
            // 应用
            userLabelAlgorithmMapper.updateUserLabelAlgorithm(userId,labelId,1);
        }else{
            //取消
            userLabelAlgorithmMapper.updateUserLabelAlgorithm(userId,labelId,algorithmId);
        }
    }
}
