package com.timyang.playground.api.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
