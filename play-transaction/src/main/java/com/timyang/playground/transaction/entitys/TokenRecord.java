package com.timyang.playground.transaction.entitys;

import java.time.LocalDateTime;

public class TokenRecord {

    private String username;
    private String token;
    private LocalDateTime genTime;

    public TokenRecord(String username, String token, LocalDateTime genTime) {
        this.username = username;
        this.token = token;
        this.genTime = genTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getGenTime() {
        return genTime;
    }

    public void setGenTime(LocalDateTime genTime) {
        this.genTime = genTime;
    }

}
