package com.zengqiang.future.common;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;

public class MyRedisCacheManager extends RedisCacheManager {
    public MyRedisCacheManager(RedisOperations redisOperations) {
        super(redisOperations);
    }

    public MyRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
        super(redisOperations, cacheNames);
    }

    public MyRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames, boolean cacheNullValues) {
        super(redisOperations, cacheNames, cacheNullValues);
    }

    //通过前缀识别一类缓存信息
    @Override
    protected long computeExpiration(String name) {
        String[] p=name.split("-");
        return super.computeExpiration(p[0]);
    }
}
