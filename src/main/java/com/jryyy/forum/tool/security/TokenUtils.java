package com.jryyy.forum.tool.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jryyy.forum.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

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
     * 创建jwt
     *
     * @param user the token payload
     * @return the JWT token
     */
    public String createJwtToken(User user) {
        final Date expiration = new Date(System.currentTimeMillis() + expirationInSeconds * 1000);
        try {
            return Jwts.builder().claim(CLAIM_KEY, objectMapper.writeValueAsString(user)).setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS512, secret).compact();
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("无法创建JWT令牌", ex);
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
    public User decodeJwtToken(String jwtToken) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
        } catch (Exception ex) {
            throw new IllegalArgumentException("无效的JWT令牌", ex);
        }
        String payload = (String) claims.get(CLAIM_KEY);
        try {
            return objectMapper.readValue(payload, User.class);
        } catch (IOException ex) {
            throw new IllegalArgumentException("无效的JWT令牌", ex);
        }
    }
}

