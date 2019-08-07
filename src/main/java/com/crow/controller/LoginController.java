package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.entity.User;
import com.crow.service.LoginService;
import com.crow.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 王衍庆
 * Changed by wuy2 2019.8.7
 */

@CrossOrigin
@RestController
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/wsnews/login",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String loginByWechatAccount(@RequestBody Map<String, String> map,HttpServletRequest request, HttpServletResponse response){
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
