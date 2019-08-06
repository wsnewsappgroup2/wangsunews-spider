package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crow.dao.UserMapper;
import com.crow.entity.User;
import com.crow.utils.HttpClientUtil;
import com.crow.utils.WechatApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    public User login(String code,String username,String sex){
        // 向微信请求授权登录
        JSONObject validateResult= validateByWechat(code);
        // 依照不同的响应码做处理
        int errorCode=validateResult.getIntValue("errcode");
        User user=null;
        switch (errorCode){
            case 0:
                // 授权请求成功
                validateResult.put("username",username);
                validateResult.put("sex",sex);
                user= getUserByValidateResult(validateResult);
                if(user!=null)
                    break;
            case 40029:
                // 无效的临时登录凭证 code
            case -1:
                // 系统繁忙，此时请开发者稍候再试
            case 45011:
                // 频率限制，每个用户每分钟100次
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
        // 尝试从数据库中获取
        user=getUserByOpenId(openid);
        if(user==null){
            // 数据库中赞无用户
            user= JSON.toJavaObject(validateResult,User.class);
            saveUserByValidateResult(user);
        }
        return user;
    }

    /**从数据库中获取对应的用户信息**/
    private User getUserByOpenId(String openid){
        List<User> users=userMapper.getUser(openid);
        return users.size()<=0? null:users.get(0);
    }

    /**插入新的用户信息**/
    private void saveUserByValidateResult(User user){
        userMapper.insert(user);
    }
}
