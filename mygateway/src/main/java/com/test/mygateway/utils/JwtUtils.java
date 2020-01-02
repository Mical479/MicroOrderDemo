package com.test.mygateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.test.mygateway.exception.CommonEnum;
import com.test.mygateway.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

/**
 * jwt
 *
 * @author MicalJ
 * @create 2019/12/5 11:42
 **/
@Slf4j
@SuppressWarnings("all")
public class JwtUtils {

    private static String KEY = "sfsdfjiaofjiw";
    private static long ttlMillis = 1000000;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(KEY);
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(Integer userId, String userName) throws Exception {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .claim("userId", userId)
                .setSubject(userName)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setIssuer("sys-user")          // issuer：jwt签发人
                .setId(UUID.randomUUID().toString())                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(jsonWebToken).getBody();
        return claims;
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUsername(String token) {
        return parseJWT(token).getSubject();
    }

    /**
     * 从token中获取用户ID
     *
     * @param token
     * @param base64Security
     * @return
     */
    public static Integer getUserId(String token) {
        Integer userId = parseJWT(token).get("userId", Integer.class);
        return userId;
    }

    /**
     * 是否已过期，当离过期时间还有5分钟，就默认为过期，应该重新设置token
     * @param token
     * @return true: 没过期，false：过期了
     */
    public static boolean isExpiration(Claims claims){
        long time = claims.getExpiration().getTime();
        if (time - new Date().getTime() < 1000 * 60 * 5){
            return false;
        }
        return true;
    }
}
