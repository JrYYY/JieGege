package com.jryyy.forum.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Redis 封装实例工具
 * @author JrYYY
 */
@Component
public class RedisUtils{

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Long deleteHash(String key,Object... objects){
        return redisTemplate.opsForHash().delete(key,objects);
    }

    public Object getString(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public  List<Object> getList(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

    public Object getHash(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    public <T> List<T> getHashList(String key, String field, Class<T> obj) {
        Object value = redisTemplate.opsForHash().get(key, field);
        if (value != null) {
            return JSONObject.parseArray(value.toString(), obj);
        } else {
            return new ArrayList<>();
        }
    }


    public void rightPushHashList(String key,String field,Object values){
        List data = getHashList(key,field,values.getClass());
        if(data == null){
            data = new ArrayList();
        }
        data.add(values);
        setHashList(key,field,data);
    }

    public <T> void setHashList(String key, String field, List<T> values) {
        String v = JSONObject.toJSONString(values);
        redisTemplate.opsForHash().put(key, field, v);
    }


}
