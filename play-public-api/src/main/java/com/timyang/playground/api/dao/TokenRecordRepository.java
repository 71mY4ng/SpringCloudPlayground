package com.timyang.playground.api.dao;

import com.timyang.playground.api.entitys.TokenRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRecordRepository {

    void createTokenRecord(TokenRecord record);
    void updateTokenRecordByUsername(String username, TokenRecord record);
    TokenRecord getTokenRecordByUsername(String username);
}
