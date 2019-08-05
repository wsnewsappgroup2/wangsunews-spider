package com.crow.service;

import com.alibaba.fastjson.JSONObject;
import com.crow.utils.HttpClientUtil;
import com.crow.utils.WechatApiUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    public String varifyByWechat(String code){
        // 配置请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", WechatApiUtil.WX_APP_ID);
        param.put("secret", WechatApiUtil.WX_APP_SECRET);
        param.put("js_code", code);
        param.put("grant_type", WechatApiUtil.WX_GRANT_TYPE);

        // 向微信API发送请求
        String wxResult = HttpClientUtil.doGet(WechatApiUtil.WX_AUTH_CODE2SESSION_URL, param);
        return wxResult;
    }

    // 保存授权的用户的数据
    public void saveUserIfNotExists(JSONObject object){

    }
}
