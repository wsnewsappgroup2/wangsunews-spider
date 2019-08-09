package com.crow.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created By wangyq1
 * 用于用户个人相关的响应控制
 * */
@RestController
public class UserController {
    // 新增个人栏目
    //@PutMapping("/wsnews/column/add_column")
    public void addPersonalColumn(@RequestBody Map<String,String> map){}
    // 删除个人栏目
    //@DeleteMapping("/wsnews/column/delete_column")
    public void deletePersonalColumn(){}
    // 获取栏目下应用算法
    public void getAppliedAlgorithmInColumn(){}
}
