package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.service.LoginService;
import com.crow.utils.HttpClientUtil;
import com.crow.utils.WechatApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 王衍庆
 * **/
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/wsnews/login")
    public String user_login(@RequestParam("code") String code){

        // 授权校验登录
        JSONObject jsonObject = JSONObject.parseObject(loginService.varifyByWechat(code));

        // 保存用户信息
        loginService.saveUserIfNotExists(jsonObject);

        return "OK";

//        // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
//        User user = userService.selectByOpenId(open_id);
//        if(user != null){
//            user.setUserNewLogin(new Date());
//            userService.updateById(user);
//        }else{
//            User insert_user = new User();
//            insert_user.setUserHead(userHead);
//            insert_user.setUserName(userName);
//            insert_user.setUserGender(userGender);
//            insert_user.setUserNewLogin(new Date());
//            insert_user.setUserCity(userCity);
//            insert_user.setUserProvince(userProvince);
//            insert_user.setUserOpenid(open_id);
//            System.out.println("insert_user:"+insert_user.toString());
//            // 添加到数据库
//            Boolean flag = userService.insert(insert_user);
//            if(!flag){
//                return new JsonResult(ResultCode.FAIL);
//            }
//        }
//        // 封装返回小程序
//        Map<String, String> result = new HashMap<>();
//        result.put("session_key", session_key);
//        result.put("open_id", open_id);
//        return new JsonResult(ResultCode.SUCCESS, result);
    }
}
