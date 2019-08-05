package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.entity.User;
import com.crow.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author 王衍庆
 * **/
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/wsnews/login",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String loginByWechatAccount(@RequestBody Map<String, String> map){
        // 用户登录
        User user=loginService.login(map.get("code"),map.get("username"));
        String msg="Login Failed";
        String openid="-1";
        if(user==null){
            // 授权登录失败
        }else{
            // 成功
            openid=user.getOpenid();
            msg="Login Success";
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("open_id",openid);
        jsonObject.put("msg",msg);
        String res=jsonObject.toJSONString();
        return res;
    }
}
