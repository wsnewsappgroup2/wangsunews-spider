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

/**
 * Created by 王衍庆
 */
@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    public String login(String code,String username,String sex){
        // 向微信请求授权登录
        JSONObject validateResult= validateByWechat(code);
        int errorCode=validateResult.getIntValue("errcode");
        User user=null;

        if(errorCode==0){
            // 授权请求成功
            validateResult.put("username",username);
            validateResult.put("sex",sex);
            user= getUserByValidateResult(validateResult);
        }

        JSONObject validateResponse=new JSONObject();
        if(user!=null){
            // 登录授权成功响应
            validateResponse.put("msg","Login Successfully --- Wechat User: "+username);
            validateResponse.put("open_id",user.getOpenid());
            validateResponse.put("success",true);
        }else{
            switch (errorCode){
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
            // 登录授权失败响应
            validateResponse.put("msg","Login Failed --- Wechat User: "+username+"Error Code: "+errorCode);
            validateResponse.put("open_id",null);
            validateResponse.put("success",false);
        }
        return validateResponse.toJSONString();
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

        // 尝试从数据库中获取
        User user=null;
        user=getUserByOpenId(openid);

        // 存在用户则更新session_key否则插入新的用户
        if(user!=null){
            userMapper.updateSesssionKeyByOpenId(openid,validateResult.getString("session_key"));
        }else{
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
