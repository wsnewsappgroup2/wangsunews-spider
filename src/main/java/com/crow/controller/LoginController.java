package com.crow.controller;

import com.alibaba.fastjson.JSONObject;
import com.crow.service.LoginService;
import com.crow.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wangyq1
 * Last Modified By wangyq1 2019.8.8
 */
@RestController
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/wsnews/login",consumes = "application/json",produces = "application/json;charset=UTF-8")
    public String silentVarifyLogin(
            @RequestBody(required = false) Map<String, String> map){
        // 静默登录
        JSONObject validateResponse= loginService.silentLogin(map.get("code"));
        String openid=validateResponse.getString("open_id");

        String token = null;
        try {
            token = JwtUtil.sign(openid);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        validateResponse.remove("open_id");
        validateResponse.put("token",token);
        return validateResponse.toJSONString();
    }

}
