package com.timyang.playground.transaction.entitys;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {

    private String itemId;
    private String itemName;

    private String sellerId;
    private String sellerName;

    private BigDecimal price;
    private Integer quantity;

}
