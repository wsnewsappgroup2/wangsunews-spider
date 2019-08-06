package com.crow.utils;


public class WechatApiUtil {
    // 小程序 appid
    public static final String WX_APP_ID="wxcf9def03ec2dce71";

    // 小程序 appsecret
    public static final String WX_APP_SECRET="035f1ad28e25dbb4a955f95292d8e17b";

    // 登录校验的时固定授权类型
    public static final String WX_GRANT_TYPE="authorization_code";

    // 登录凭证校验URL (GET)
    public static final String WX_AUTH_CODE2SESSION_URL="https://api.weixin.qq.com/sns/jscode2session";

}
