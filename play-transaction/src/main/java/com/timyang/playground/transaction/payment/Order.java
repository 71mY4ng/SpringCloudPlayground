package com.timyang.playground.transaction.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private String id;
    private LocalDateTime createTime;
    private String itemId;
    private BigDecimal itemPrice;
    private String customerId;
    private String shopId;
    private Integer quantity;
    private BigDecimal orderPrice;
    private String state;
    private LocalDateTime lastUpdateTime;
}
