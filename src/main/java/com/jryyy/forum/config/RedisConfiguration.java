package com.jryyy.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * @author JrYYY
 */
@Configuration
public class RedisConfiguration {

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisConfiguration(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jsonString = new GenericToStringSerializer<>(Object.class);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonString);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonString);
        return redisTemplate;
    }


}