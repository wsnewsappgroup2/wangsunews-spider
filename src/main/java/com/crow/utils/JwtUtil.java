package com.crow.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;


public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            String encryptJWTKey = "encryptJWTKey";
            // 帐号加JWT私钥加密
            String secret = Base64ConvertUtil.decode(encryptJWTKey);
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            logger.error("TOKEN verify" + e.getMessage());
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的openid
     */
    public static String getOpenid(String token) {
        try {
            if(token != null) {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim("openid").asString();
            }
        } catch (JWTDecodeException e) {
            return null;
        }
        return null;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的电话
     */
    public static String getTelNo(String token) {
        try {
            if(token != null) {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim("telNo").asString();
            }
        } catch (JWTDecodeException e) {
            return null;
        }
        return null;
    }

    /**
     * 生成签名,**过期
     *
     * @param openid 用户名
     * @return 加密的token
     */
    public static String sign(String openid) throws Exception {
        try {

            String encryptJWTKey = "why_key_Hello_scp_049";
            // 帐号加JWT私钥加密
            String secret = openid + Base64.getEncoder().encodeToString(encryptJWTKey.getBytes());
            String accessTokenExpireTime = "36000000000000000";//毫秒
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username,hotel信息
            return JWT.create()
                    .withClaim("openid", openid)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e) {
            logger.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new Exception("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

}
