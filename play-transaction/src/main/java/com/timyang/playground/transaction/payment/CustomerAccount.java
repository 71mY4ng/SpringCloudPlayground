package com.timyang.playground.transaction.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerAccount {
    private String id;
    private String customerId;
    private String accountId;
    private String accountType;
    private BigDecimal balance;
    private LocalDateTime createTime;
    private String state;
    private LocalDateTime lastUpdateTime;
}
