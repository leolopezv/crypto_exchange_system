package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.OrderBook;
import org.example.model.SellOrder;
import org.example.service.iService.IBalanceService;
import org.example.service.iService.IOrderService;

import java.math.BigDecimal;

public class OrderService implements IOrderService {
    private final OrderBook orderBook;
    private final IBalanceService balanceService;

    public OrderService(IBalanceService balanceService, OrderBook orderBook) {
        this.balanceService = balanceService;
        this.orderBook = orderBook;
    }

    @Override
    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BigDecimal newOrderCost = amount.multiply(maxPrice);

        if (!balanceService.hasSufficientFiat(userId, newOrderCost)) {
            System.out.println("Insufficient fiat to place buy order.");
            return;
        }
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);
        orderBook.matchOrders(order);
    }

    @Override
    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        if (!balanceService.hasSufficientCrypto(userId, cryptoSymbol, amount)) {
            System.out.println("Insufficient crypto to place sell order.");
            return;
        }
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        orderBook.matchOrders(order);
    }
}
