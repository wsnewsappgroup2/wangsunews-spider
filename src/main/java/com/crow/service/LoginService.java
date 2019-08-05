package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crow.entity.User;
import com.crow.utils.HttpClientUtil;
import com.crow.utils.WechatApiUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {


    public User login(String code,String username){
        // 向微信请求授权登录
        JSONObject validateResult= validateByWechat(code);
        // 依照不同的响应码做处理
        int errorCode=validateResult.getIntValue("errcode");
        User user=null;
        switch (errorCode){
            case 0:
                // 授权请求成功
                validateResult.put("username",username);
                user= getUserByValidateResult(validateResult);
                if(user!=null)
                    break;
            case 40029:
                // 无效的临时登录凭证 code
                // break;
            case -1:
                // 系统繁忙，此时请开发者稍候再试
                // break;
            case 45011:
                // 频率限制，每个用户每分钟100次
                // break;
            default:
                // 异常响应码
                break;
        }
        return user;
    }


    /**向微信请求授权登录**/
    private JSONObject validateByWechat(String code){
        // 配置请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", WechatApiUtil.WX_APP_ID);
        param.put("secret", WechatApiUtil.WX_APP_SECRET);
        param.put("js_code", code);
        param.put("grant_type", WechatApiUtil.WX_GRANT_TYPE);

        // 向微信API发送请求
        String wxResult = HttpClientUtil.doGet(WechatApiUtil.WX_AUTH_CODE2SESSION_URL, param);
        JSONObject jsonObject=JSONObject.parseObject(wxResult);
        return jsonObject;
    }

    /**获取授权的用户信息，如果是初次使用则会先保存用户信息**/
    private User getUserByValidateResult(JSONObject validateResult){
        String openid=validateResult.getString("openid");
        String session_key=validateResult.getString("session_key");
        String unionid=validateResult.getString("unionid");

        User user=null;
        if(exists(openid)){
            user=getUserByOpenId(openid);
        }else{
            user= JSON.toJavaObject(validateResult,User.class);
        }
        return user;
    }

    /**判断用户是否在数据库中**/
    private boolean exists(String openid){
        // TODO: 完善判断语句
        return false;
    }

    /**从数据库中获取对应的用户信息**/
    private User getUserByOpenId(String openid){
        // TODO:修改逻辑为正确逻辑并和dao层对接
        User user=new User();
        user.setOpenid("test_openid_123");
        user.setSession_key("test_session_key_123");
        user.setUnionid("test_unionid_123");
        user.setUsername("test_username_123");
        return user;
    }

    private void saveUserByValidateResult(User user){
        // TODO:修改逻辑为正确逻辑并和dao层对接
        System.out.println("Saved Successfully");
        return;
    }
}
