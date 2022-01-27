package com.timyang.playground.transaction.payment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {
    private String id;
    private String customerName;
    private String mobileNum;
    private String email;
    private LocalDateTime registerDate;
}
