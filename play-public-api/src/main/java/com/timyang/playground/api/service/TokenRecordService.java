package com.timyang.playground.api.service;

import com.timyang.playground.api.entitys.TokenRecord;
import org.springframework.stereotype.Service;

@Service
public interface TokenRecordService {

    void saveTokenRecord(TokenRecord record);

    void updateTokenRecord(String username, TokenRecord record);
}
