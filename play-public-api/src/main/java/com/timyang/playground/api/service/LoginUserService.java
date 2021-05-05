package com.timyang.playground.api.service;

import com.timyang.playground.api.controller.dto.UserRegistrationRequest;
import com.timyang.playground.api.entitys.LoginUser;
import org.springframework.stereotype.Service;

@Service
public interface LoginUserService {
    boolean checkSecret(String compare, String existed);
    void save(UserRegistrationRequest register);
    LoginUser findUserByUsername(String username);
}
