package com.timyang.playground.api.entitys;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginUser implements Serializable {
    private String id;
    private String username;
    private String password;
}
