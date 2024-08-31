package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.Order;
import org.example.model.OrderBook;
import org.example.model.SellOrder;

import java.math.BigDecimal;

public class OrderService {
    private final OrderBook orderBook;
    private final BalanceService balanceService;
    private final TransferService transferService;

    public OrderService(OrderBook orderBook, BalanceService balanceService, TransferService transferService) {
        this.orderBook = orderBook;
        this.balanceService = balanceService;
        this.transferService = transferService;
    }

    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);

        BigDecimal totalCost = amount.multiply(maxPrice);
        if (!balanceService.hasSufficientFiat(userId, totalCost)) {
            System.out.println("Error: Insufficient fiat balance to place buy order.");
            return;
        }

        orderBook.matchOrders(order);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        orderBook.matchOrders(order);
    }

    public void executeTransaction(BuyOrder buyOrder, SellOrder sellOrder) {
        transferService.transferCrypto(sellOrder.getUserId(), buyOrder.getUserId(), sellOrder.getCryptoSymbol(), sellOrder.getAmount());

        BigDecimal totalCost = sellOrder.getMinPrice().multiply(sellOrder.getAmount());
        transferService.transferFiat(buyOrder.getUserId(), sellOrder.getUserId(), totalCost);

        orderBook.removeOrder(buyOrder);
        orderBook.removeOrder(sellOrder);
    }
}
