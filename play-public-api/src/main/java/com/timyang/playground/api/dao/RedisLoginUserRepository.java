package com.timyang.playground.api.dao;

import com.timyang.playground.api.entitys.LoginUser;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component("redisLoginUserRepository")
public class RedisLoginUserRepository implements LoginUserRepository {

    private final RedissonClient redissonClient;
    private static final String CREDENTIAL_PREFIX = "login:cred:";
    private static final String IDX_PREFIX = "login:idx_uname_id";

    public RedisLoginUserRepository(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void save(LoginUser user) {
        RBucket<LoginUser> bucket = redissonClient.getBucket(CREDENTIAL_PREFIX + user.getId());
        bucket.trySet(user);
        redissonClient.getMap(IDX_PREFIX).put(user.getUsername(), user.getId());
    }

    @Override
    public void update(LoginUser updated) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(String id) {
        throw new NotImplementedException();
    }

    @Override
    public LoginUser findByUserName(String username) {
        String id = redissonClient.<String, String>getMap(IDX_PREFIX).get(username);

        RBucket<LoginUser> bucket = redissonClient.getBucket(CREDENTIAL_PREFIX + id);
        return bucket.get();
    }

    @Override
    public LoginUser get(String id) {
        RBucket<LoginUser> bucket = redissonClient.getBucket(CREDENTIAL_PREFIX + id);
        return bucket.get();
    }
}
