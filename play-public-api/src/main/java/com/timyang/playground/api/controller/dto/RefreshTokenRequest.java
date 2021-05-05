package com.timyang.playground.api.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String refreshToken;
}
