package com.crow.utils;


public class WechatApiUtil {
    // 小程序 appid
    // TODO: 填充appid
    public static final String WX_APP_ID="XX";

    // 小程序 appsecret
    // TODO：appsecret
    public static final String WX_APP_SECRET="XX";

    // 登录校验的时固定授权类型
    public static final String WX_GRANT_TYPE="authorization_code";

    // 登录凭证校验URL (GET)
    public static final String WX_AUTH_CODE2SESSION_URL="https://api.weixin.qq.com/sns/jscode2session";

}
