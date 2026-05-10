package com.example.apartmentmanagement.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtUtils {

    // 令牌加密密钥
    private static final String SECRET_KEY = "Apartment123!@#";

    // 生成用户授权令牌
    public static String createToken(String username) {
        return JWT.create()
                .withClaim("username", username) // 设置用户名载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) // 设置12小时有效期
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 执行签名加密
    }
}