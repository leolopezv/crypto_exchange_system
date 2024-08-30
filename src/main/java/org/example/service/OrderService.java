package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.Order;
import org.example.model.OrderBook;
import org.example.model.SellOrder;
import org.example.repository.OrderRepository;

import java.math.BigDecimal;

public class OrderService {
    private final OrderBook orderBook;

    public OrderService(OrderRepository orderRepository, WalletService walletService) {
        this.orderBook = new OrderBook(orderRepository, walletService);
    }

    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);
        orderBook.matchOrders(order);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        orderBook.matchOrders(order);
    }
}
