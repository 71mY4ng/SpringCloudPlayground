package com.timyang.playground.transaction.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRecord {
    private String id;
    private String orderId;
    private String orderPrice;
    private String customerId;
    private String accountId;
    private String merchantAccountId;
    private String channel;
    private String startTime;
    private String state;
    private String settleTime;
}
