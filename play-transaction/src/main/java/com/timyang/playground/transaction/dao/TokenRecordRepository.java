package com.timyang.playground.transaction.dao;

import com.timyang.playground.transaction.entitys.TokenRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRecordRepository {

    void createTokenRecord(TokenRecord record);
    void updateTokenRecordByUsername(String username, TokenRecord record);
    TokenRecord getTokenRecordByUsername(String username);
}
