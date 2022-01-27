package com.timyang.playground.transaction.dao;

import com.timyang.playground.transaction.Tables;
import com.timyang.playground.transaction.dao.dto.SellerBidDTO;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component("dBSellerBidRepositoryImpl")
public class DBSellerBidRepositoryImpl implements SellerBidRepository {

    private final DSLContext dslContext;

    public DBSellerBidRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void save(SellerBidDTO bid) {
        this.dslContext.insertInto(Tables.M_SELL_BID);
    }
}
