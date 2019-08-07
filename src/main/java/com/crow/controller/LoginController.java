package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.entity.User;
import com.crow.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 王衍庆
 */

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/wsnews/login",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String loginByWechatAccount(@RequestBody Map<String, String> map){
        // 用户登录
        return loginService.login(map.get("code"),map.get("username"),map.get("sex"));
    }

}
