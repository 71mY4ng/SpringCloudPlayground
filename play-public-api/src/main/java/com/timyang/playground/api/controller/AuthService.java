package com.timyang.playground.api.controller;

import com.timyang.playground.api.controller.dto.AuthRequest;
import com.timyang.playground.api.controller.dto.RefreshTokenRequest;
import com.timyang.playground.api.entitys.TokenRecord;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public TokenRecord refreshToken(RefreshTokenRequest req);

    public TokenRecord login(AuthRequest req);
}
