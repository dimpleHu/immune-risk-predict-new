package com.immuserisk.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String secret = "skyfeeling_jwt_secret_key_must_be_long_enough";
    private static final long expire = 24 * 60 * 60 * 1000; // 24小时
    private static final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成管理员token
     */
    public static String generateToken(Integer adminId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        logger.debug("生成管理员Token，adminId：{}", adminId);
        String token = Jwts.builder()
                .setSubject(adminId.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        logger.debug("管理员Token生成成功：{}", token);
        return token;
    }

    /**
     * 生成用户token
     */
    public static String generateUserToken(Integer userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        logger.debug("生成用户Token，userId：{}", userId);
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .claim("type", "user")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        logger.debug("用户Token生成成功：{}", token);
        return token;
    }

    public static Claims getClaimsFromToken(String token) {
        try {
            logger.debug("解析Token：{}", token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            logger.debug("Token解析成功，claims：{}", claims);
            return claims;
        } catch (Exception e) {
            logger.error("Token解析失败：{}，异常：{}", token, e.getMessage(), e);
            throw e;
        }
    }

    public static boolean validateToken(String token) {
        try {
            logger.debug("验证Token：{}", token);
            Claims claims = getClaimsFromToken(token);
            boolean valid = !claims.getExpiration().before(new Date());
            logger.debug("Token验证结果：{}", valid ? "有效" : "无效");
            return valid;
        } catch (Exception e) {
            logger.error("Token验证失败：{}，异常：{}", token, e.getMessage(), e);
            return false;
        }
    }

    public static Integer getAdminIdFromToken(String token) {
        try {
            logger.debug("获取管理员ID，Token：{}", token);
            Claims claims = getClaimsFromToken(token);
            Integer adminId = Integer.parseInt(claims.getSubject());
            logger.debug("获取管理员ID成功：{}", adminId);
            return adminId;
        } catch (Exception e) {
            logger.error("获取管理员ID失败，Token：{}，异常：{}", token, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 从token中获取用户ID
     */
    public static Integer getUserIdFromToken(String token) {
        try {
            logger.debug("获取用户ID，Token：{}", token);
            Claims claims = getClaimsFromToken(token);
            String type = claims.get("type", String.class);
            if (!"user".equals(type)) {
                logger.error("无效的用户Token，type：{}", type);
                throw new IllegalArgumentException("Invalid user token");
            }
            Integer userId = Integer.parseInt(claims.getSubject());
            logger.debug("获取用户ID成功：{}", userId);
            return userId;
        } catch (Exception e) {
            logger.error("获取用户ID失败，Token：{}，异常：{}", token, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 判断是否为用户token
     */
    public static boolean isUserToken(String token) {
        try {
            logger.debug("判断是否为用户Token：{}", token);
            Claims claims = getClaimsFromToken(token);
            String type = claims.get("type", String.class);
            boolean isUser = "user".equals(type);
            logger.debug("Token类型判断结果：{}", isUser ? "用户Token" : "非用户Token");
            return isUser;
        } catch (Exception e) {
            logger.error("Token类型判断失败：{}，异常：{}", token, e.getMessage(), e);
            return false;
        }
    }
}