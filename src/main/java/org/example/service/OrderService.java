package org.example.service;

import org.example.model.*;
import org.example.repository.iRepository.TransactionRepository;
import org.example.service.iService.IBalanceService;
import org.example.service.iService.IOrderService;

import java.math.BigDecimal;
import java.util.List;

public class OrderService implements IOrderService {
    private final OrderBook orderBook;
    private final IBalanceService balanceService;
    private final TransactionRepository transactionRepository;

    public OrderService(IBalanceService balanceService, OrderBook orderBook, TransactionRepository transactionRepository) {
        this.balanceService = balanceService;
        this.orderBook = orderBook;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BigDecimal newOrderCost = amount.multiply(maxPrice);

        if (!balanceService.hasSufficientFiat(userId, newOrderCost)) {
            System.out.println("Insufficient fiat to place buy order.");
            return;
        }
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);
        System.out.println("Buy order successfully placed.");
        orderBook.matchOrders(order);
    }

    @Override
    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        if (!balanceService.hasSufficientCrypto(userId, cryptoSymbol, amount)) {
            System.out.println("Insufficient crypto to place sell order.");
            return;
        }
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        System.out.println("Sell order successfully placed.");
        orderBook.matchOrders(order);
    }

    @Override
    public void showPastTransactions(int userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}