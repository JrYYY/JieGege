package com.jryyy.forum.utils.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This utility class contains helper methods used to generate and decode JWT
 * tokens.
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
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 缓存
     */
    @Autowired
    private RedisTemplate<String, String> template;

//    @Autowired
//    ValueOperations operations;

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
            template.opsForValue().set(user.getId().toString(), token, 10L, TimeUnit.DAYS);
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
        try {
            User user = objectMapper.readValue(payload, User.class);
            String token = template.opsForValue().get(user.getId().toString());
            if (token == null || token.isEmpty() || !token.equals(jwtToken))
                throw new GlobalException(GlobalStatus.alreadyLoggedInElsewhere);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GlobalException(GlobalStatus.invalidToken);
        }
    }


    public void deleteJWTToken(Integer userId) throws Exception {
        template.delete(userId.toString());
    }
}

