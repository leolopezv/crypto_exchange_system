package org.example.controller;

import org.example.model.Exchange;
import org.example.service.OrderService;
import org.example.view.MoneyView;

import java.math.BigDecimal;

public class OrderController {
    private final OrderService orderService;
    private final MoneyView moneyView;
    private final Exchange exchange = Exchange.getInstance();

    public OrderController(OrderService orderService, MoneyView moneyView) {
        this.orderService = orderService;
        this.moneyView = moneyView;
    }

    public void placeBuyOrder(int userId) {
        String cryptoSymbol = moneyView.getCryptoSymbol(exchange.getAllCryptoSymbols());
        BigDecimal amount = moneyView.getInfoAmount(exchange, cryptoSymbol);
        BigDecimal maxPrice = moneyView.getUserAmount("How much are you willing to pay?: ");
        orderService.placeBuyOrder(userId, cryptoSymbol, amount, maxPrice);
    }

    public void placeSellOrder(int userId) {
        String cryptoSymbol = moneyView.getCryptoSymbol(exchange.getAllCryptoSymbols());
        BigDecimal amount = moneyView.getInfoAmount(exchange, cryptoSymbol);
        BigDecimal minPrice = moneyView.getUserAmount("Enter the minimum price you are willing to accept: ");
        orderService.placeSellOrder(userId, cryptoSymbol, amount, minPrice);
    }

    public void showPastTr(int userId) {
        orderService.showHistory(userId);
    }
}