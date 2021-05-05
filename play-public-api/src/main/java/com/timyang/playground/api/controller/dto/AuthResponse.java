package com.timyang.playground.api.controller.dto;

import com.timyang.playground.api.entitys.TokenRecord;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthResponse {

    private String username;
    private LocalDateTime expiresAt;
    private String refreshToken;
    private String authToken;

    public static AuthResponse parse(TokenRecord tokenRecord) {

        return AuthResponse.builder()
                .username(tokenRecord.getUsername())
                .authToken(tokenRecord.getToken())
                .expiresAt(tokenRecord.getGenTime().plusDays(18))
                .refreshToken(tokenRecord.getToken())
                .build();
    }
}
