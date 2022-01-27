package com.timyang.playground.transaction.dao;

import com.timyang.playground.transaction.dao.dto.SellerBidDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerBidRepository {

    void save(SellerBidDTO sellerBid);
}
