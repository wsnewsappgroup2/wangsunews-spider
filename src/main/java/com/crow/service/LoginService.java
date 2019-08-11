package com.crow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crow.controller.LoginController;
import com.crow.dao.UserMapper;
import com.crow.entity.User;
import com.crow.utils.HttpClientUtil;
import com.crow.utils.JwtUtil;
import com.crow.utils.WechatApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserMapper userMapper;

    /**静默登录授权获取返回包括openid**/
    public JSONObject silentLogin(String code){
        // 向微信请求授权登录
        JSONObject validateResult=null;
        try {
            validateResult= validateByWechat(code);
        }catch (NullPointerException e){
            logger.error("微信授权API调用 HTTP请求失败 ",e);
        }

        if(validateResult==null){
            JSONObject response=new JSONObject();
            response.put("msg","Cannot Get Validate Info From Wechat");
            response.put("token",null);
            response.put("success",false);
            return response;
        }

        int errorCode=validateResult.getIntValue("errcode");
        JSONObject validateResponse=new JSONObject();
        if(errorCode!=0){
            // 登录授权失败响应
            validateResponse.put("msg","Login Failed Error Code: "+errorCode);
            validateResponse.put("token",null);
            validateResponse.put("success",false);
        }else{
            // 登录授权成功响应
            validateResponse.put("msg","Login Successfully");
            String openid=validateResult.getString("openid");
            String token = null;
            try {
                token = JwtUtil.sign(openid);

                // 尝试从数据库中获取
                User user=null;
                user=getUserByOpenId(openid);

                // 存在用户则更新session_key否则插入新的用户
                if(user!=null){
                    userMapper.updateSesssionKeyByOpenId(openid,validateResult.getString("session_key"));
                }else{
                    user= JSON.toJavaObject(validateResult,User.class);
                    user.setToken(token);
                    userMapper.insertUserBasicInfo(openid,token,validateResult.getString("session_key"));
                    // 给用户添加默认的栏目收藏
                    userMapper.inserDefaultUserColumnMapping(openid,1);
                    userMapper.inserDefaultUserColumnMapping(openid,2);
                    userMapper.inserDefaultUserColumnMapping(openid,3);
                    userMapper.inserDefaultUserColumnMapping(openid,4);
                    // TODO: 给用户添加默认的启用栏目算法
                }
                validateResponse.put("token",token);
                validateResponse.put("success",true);
                validateResponse.put("msg","Got Token Successfully");
            }catch (Exception e){
                logger.error("[Error When Verifying Token]:\n",e);
                validateResponse.put("msg","Error When Getting Token");
                validateResponse.put("token",null);
                validateResponse.put("success",false);
            }
        }

        return validateResponse;
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


    /**从数据库中获取对应的用户信息**/
    private User getUserByOpenId(String openid){
        List<User> users=userMapper.getUser(openid);
        if(users==null || users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }
    }

}
