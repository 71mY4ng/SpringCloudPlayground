package com.timyang.playground.api.service;

import com.timyang.playground.api.controller.dto.UserRegistrationRequest;
import com.timyang.playground.api.dao.LoginUserRepository;
import com.timyang.playground.api.entitys.LoginUser;
import com.timyang.playground.api.util.IdGenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("loginUserService")
public class LoginUserServiceImpl implements LoginUserService {

    private final IdGenService idGenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginUserRepository loginUserRepository;

    public LoginUserServiceImpl(IdGenService idGenService,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                LoginUserRepository redisLoginUserRepository) {
        this.idGenService = idGenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginUserRepository = redisLoginUserRepository;
    }

    @Override
    public boolean checkSecret(String compare, String existed) {
        return this.bCryptPasswordEncoder.matches(compare, existed);
    }

    @Override
    public void save(UserRegistrationRequest register) {

        loginUserRepository.save(LoginUser.builder()
                .id(idGenService.getId(LocalDateTime.now()))
                .username(register.getUsername())
                .password(bCryptPasswordEncoder.encode(register.getPassword()))
                .build());
    }

    @Override
    public LoginUser findUserByUsername(String username) {
        return loginUserRepository.findByUserName(username);
    }


}
