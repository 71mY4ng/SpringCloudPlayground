package com.timyang.playground.transaction;

import com.timyang.playground.transaction.entitys.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = TransactionApplication.class)
@RunWith(SpringRunner.class)
public class MarketTest {

    @Test
    public void testPutOnSale() {

        final Integer INITIAL_QUANTITY = 1;
        Market mockMarket = mock(Market.class);
        Account account = mock(Account.class);
        Item mockItem = mock(Item.class);
        Inventory inventory = mock(Inventory.class);
        Product product = Product.builder()
                .itemId(mockItem.getItemNo())
                .sellerId(account.getAccountNo())
                .price(BigDecimal.TEN)
                .quantity(INITIAL_QUANTITY)
                .build();

        when(mockMarket.getProduct(mockItem.getItemNo(), account.getAccountNo()))
                .thenReturn(product);
        when(account.getInventory())
                .thenReturn(inventory);
        when(inventory.getCarried())
                .thenReturn(inventory);

        final Integer itemCarried = account.getInventory().getCarried().get(mockItem);

        // sell item to market
        account.sell(mockMarket, mockItem, 12, BigDecimal.TEN);

        Assert.assertFalse(account.getInventory().getCarried().containsKey(mockItem));

        Optional<Product> sold = Optional.ofNullable(mockMarket.getProduct(mockItem.getItemNo(), account.getAccountNo()));

        Assert.assertTrue(sold.isPresent());
        Assert.assertEquals(itemCarried + INITIAL_QUANTITY, (int) sold.get().getQuantity());

    }
}
