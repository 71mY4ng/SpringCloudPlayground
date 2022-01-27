package com.timyang.playground.transaction.entitys;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class Market {

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Product getProduct(String itemId, String userId) {

//        final Optional<Product> sold = mockMarket.getProductList().stream()
//                .filter(product -> StringUtils.equals(product.getItemId(), mockItem.getItemId())
//                        && StringUtils.equals(product.getSellerId(), user.getUserId()))
//                .findFirst();
//        return sold;

        return null;
    }
}
