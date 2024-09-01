package org.example.controller;

import org.example.model.Exchange;
import org.example.service.OrderService;
import org.example.view.MoneyView;

import java.math.BigDecimal;
import java.util.List;

public class OrderController {
    private final OrderService orderService;
    private final MoneyView moneyView;
    private final Exchange exchange = Exchange.getInstance();

    public OrderController(OrderService orderService, MoneyView moneyView) {
        this.orderService = orderService;
        this.moneyView = moneyView;
    }

    public void placeBuyOrder(int userId) {
        List<String> validSymbols = exchange.getAllCryptoSym();
        String cryptoSymbol = moneyView.getCryptoSymbol(validSymbols);
        exchange.getCryptoBySym(cryptoSymbol).showMarketPrice();
        BigDecimal amount = moneyView.getUserAmount("Enter the amount of crypto you want to buy: ");
        BigDecimal maxPrice = moneyView.getUserAmount("Enter the maximum price you are willing to pay: ");
        orderService.placeBuyOrder(userId, cryptoSymbol, amount, maxPrice);
    }

    public void placeSellOrder(int userId) {
        List<String> validSymbols = exchange.getAllCryptoSym();
        String cryptoSymbol = moneyView.getCryptoSymbol(validSymbols);
        exchange.getCryptoBySym(cryptoSymbol).showMarketPrice();
        BigDecimal amount = moneyView.getUserAmount("Enter the amount of crypto you want to sell: ");
        BigDecimal minPrice = moneyView.getUserAmount("Enter the minimum price you are willing to accept: ");
        orderService.placeSellOrder(userId, cryptoSymbol, amount, minPrice);
    }

    public void showPastTr(int userId) {
        orderService.showHistory(userId);
    }
}