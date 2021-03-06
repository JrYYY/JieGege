package com.jryyy.forum.utils.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This utility class contains helper methods used to generate and decode JWT
 * tokens.
 * @author OU
 */
@Component
public class TokenUtils {

    /**
     * The claim key.
     */
    private static final String CLAIM_KEY = "sub";

    /**
     * The secret to sign the tokens.
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * The token expiration in seconds.
     */
    @Value("${token.effectiveTime}")
    private long expirationInSeconds;

    /**
     * The object dao.
     */
    private final ObjectMapper objectMapper;

    /**
     * 缓存
     */
    private final RedisTemplate<String, String> template;

    private final UserInfoMapper userInfoMapper;

    public TokenUtils(RedisTemplate<String, String> template, ObjectMapper objectMapper,UserInfoMapper userInfoMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
        this.userInfoMapper = userInfoMapper;
    }


    /**
     * 创建jwt
     *
     * @param user the token payload
     * @return the JWT token
     */
    public String createJwtToken(User user) throws GlobalException {
        final Date expiration = new Date(System.currentTimeMillis() + expirationInSeconds * 1000);
        try {
            String token = Jwts.builder().claim(CLAIM_KEY, objectMapper.writeValueAsString(user)).setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS512, secret).compact();
            template.opsForValue().set(KayOrUrl.userTokenKey(user.getId()), token, 10L, TimeUnit.DAYS);
            userInfoMapper.updateRecentLoginDate(LocalDateTime.now(),user.getId());
            return token;
        } catch (JsonProcessingException ex) {
            throw new GlobalException(GlobalStatus.unableToCreateToken);
        }
    }

    /**
     * 解析jwt
     *
     * @param jwtToken JWT令牌
     * @return 用户信息
     * BadCredentialsException
     * if the JWT token is invalid
     */
    public User decodeJwtToken(String jwtToken) throws GlobalException {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
        } catch (Exception ex) {
            throw new GlobalException(GlobalStatus.invalidToken);
        }
        String payload = (String) claims.get(CLAIM_KEY);
        String token = null;
        try {
            User user = objectMapper.readValue(payload, User.class);
            token = template.opsForValue().get(KayOrUrl.userTokenKey(user.getId()));
            if (token == null || token.isEmpty() || !token.equals(jwtToken)) {
                throw new GlobalException(GlobalStatus.invalidToken);
            }
            return user;
        } catch (Exception ex) {
            if (token != null && !token.equals(jwtToken)) {
                throw new GlobalException(GlobalStatus.alreadyLoggedInElsewhere);
            }
            throw new GlobalException(GlobalStatus.invalidToken);
        }


    }


    public void deleteJwtToken(Integer userId){
        template.delete(KayOrUrl.userTokenKey(userId));
    }
}

