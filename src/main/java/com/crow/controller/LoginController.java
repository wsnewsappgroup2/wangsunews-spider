package com.crow.controller;

import com.crow.service.LoginService;
import com.crow.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wangyq1
 * Last Modified By wangyq1 2019.8.8
 */
@CrossOrigin
@RestController
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/wsnews/login",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String loginByWechatAccount(@RequestBody Map<String, String> map){
        // 用户登录
        //request获取openid
        String openid = null;
        String token = null;
        try {
            token = JwtUtil.sign(openid);
        }catch (Exception e){
            logger.error(e.getMessage());


        }

        return loginService.login(map.get("code"),map.get("username"),map.get("sex"));
    }

}
