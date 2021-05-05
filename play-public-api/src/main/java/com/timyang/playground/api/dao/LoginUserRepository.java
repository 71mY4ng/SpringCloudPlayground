package com.timyang.playground.api.dao;

import com.timyang.playground.api.entitys.LoginUser;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository {
    void save(LoginUser user);
    void update(LoginUser updated);
    void delete(String id);
    LoginUser findByUserName(String username);
    LoginUser get(String id);
}
