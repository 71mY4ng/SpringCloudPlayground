package com.timyang.playground.api.dao;

import com.alibaba.fastjson.JSON;
import com.timyang.playground.api.entitys.TokenRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisTokenRecordRepository")
public class RedisTokenRecordRepositoryImpl implements TokenRecordRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String hashEntry = "cst:tokens";

    @Autowired
    public RedisTokenRecordRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createTokenRecord(TokenRecord record) {
        redisTemplate.opsForHash().putIfAbsent(hashEntry, record.getUsername(), JSON.toJSONString(record));
    }

    @Override
    public void updateTokenRecordByUsername(String username, TokenRecord record) {
        redisTemplate.opsForHash().put(hashEntry, record.getUsername(), JSON.toJSONString(record));
    }

    @Override
    public TokenRecord getTokenRecordByUsername(String username) {
        String recordJson = (String) redisTemplate.opsForHash().get(hashEntry, username);

        return JSON.parseObject(recordJson, TokenRecord.class);
    }
}
