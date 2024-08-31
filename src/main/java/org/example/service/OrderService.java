package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.OrderBook;
import org.example.model.SellOrder;

import java.math.BigDecimal;

public class OrderService {
    private final OrderBook orderBook;
    private final BalanceService balanceService;

    public OrderService(OrderBook orderBook, BalanceService balanceService) {
        this.orderBook = orderBook;
        this.balanceService = balanceService;
    }

    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);

        BigDecimal totalCost = amount.multiply(maxPrice);
        if (!balanceService.hasSufficientFiat(userId, totalCost)) {
            System.out.println("Insufficient fiat to place buy order.");
            return;
        }

        orderBook.matchOrders(order);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        orderBook.matchOrders(order);
    }

}
