package com.timyang.playground.api.controller;

import com.timyang.playground.api.controller.dto.AuthRequest;
import com.timyang.playground.api.controller.dto.RefreshTokenRequest;
import com.timyang.playground.api.dao.TokenRecordRepository;
import com.timyang.playground.api.entitys.LoginUser;
import com.timyang.playground.api.entitys.TokenRecord;
import com.timyang.playground.api.service.LoginUserService;
import com.timyang.playground.api.util.IdGenService;
import com.timyang.playground.api.util.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

@Component
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final IdGenService idGenService;
    private final TokenRecordRepository redisTokenRecordRepo;

    private final LoginUserService loginUserService;

    public AuthServiceImpl(IdGenService idGenService, TokenRecordRepository redisTokenRecordRepo,
                           LoginUserService loginUserService) {
        this.idGenService = idGenService;
        this.redisTokenRecordRepo = redisTokenRecordRepo;
        this.loginUserService = loginUserService;
    }

    @Override
    public TokenRecord refreshToken(RefreshTokenRequest req) {

        final String name = req.getUsername();

        LocalDateTime createTime = LocalDateTime.now();
        String token = idGenService.getId(createTime);

        final TokenRecord neoToken = new TokenRecord(name, token, createTime);
        redisTokenRecordRepo.updateTokenRecordByUsername(name, neoToken);

        return neoToken;
    }

    @Override
    public TokenRecord login(AuthRequest req) {
        final String username = req.getUsername();

        final Optional<LoginUser> user = Optional.ofNullable(loginUserService.findUserByUsername(username));
        if (!loginUserService.checkSecret(req.getPassword(),
                user.map(LoginUser::getPassword)
                        .orElseThrow(() -> new BadCredentialsException("incorrect login name or password")))) {
           throw new BadCredentialsException("incorrect login name or password");
        }

        Optional<TokenRecord> record = Optional.ofNullable(redisTokenRecordRepo.getTokenRecordByUsername(username));

        if (record.isPresent()) {
            TokenRecord tokenRecord = record.get();

            logger.warn("{}|{}|{}|token existed", tokenRecord.getUsername(),
                    tokenRecord.getToken(), tokenRecord.getGenTime().format(DateTimeFormatter.ISO_DATE_TIME));
            return tokenRecord;
        }

        String token = JwtTokenUtils.createToken(username, username, Collections.singletonList("normal"));
        final TokenRecord neoToken = new TokenRecord(username, token, LocalDateTime.now());
        redisTokenRecordRepo.createTokenRecord(neoToken);

        return neoToken;
    }
}
