package com.crow.service;

import com.alibaba.fastjson.JSONObject;
import com.crow.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

/**
 * Created by wangyq1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginService loginService;

    /**测试向微信获取登录授权信息**/
    @Test
    public void testValidateByWecharApi() throws Exception{
        String code="081i9FR41ByiIT1nnYR41GB0S41i9FR7";
        Class<?extends LoginService> loginServiceClass=loginService.getClass();
        Method method=loginServiceClass.getDeclaredMethod("validateByWechat",String.class);
        method.setAccessible(true);
        JSONObject validateResult=(JSONObject) method.invoke(loginService,code);

        System.out.println(validateResult.getString("openid"));
        System.out.println(validateResult.getString("session_key"));
        System.out.println(validateResult.getString("errcode"));
        System.out.println(validateResult.getString("errmsg"));
    }

    /**测试从数据库获取User信息**/
    @Test
    public void testgetUserByOpenId() throws Exception{
        String openid="";
        Class<?extends LoginService> loginServiceClass=loginService.getClass();
        Method method=loginServiceClass.getDeclaredMethod("getUserByOpenId",String.class);
        method.setAccessible(true);
        User user=(User) method.invoke(loginService,openid);
        if(user!=null){
            System.out.println(user.getOpenid());
            System.out.println(user.getSession_key());
        }
    }

}
