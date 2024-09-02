package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.Order;
import org.example.model.SellOrder;
import org.example.model.Wallet;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.WalletRepository;

import java.math.BigDecimal;
import java.util.List;

public class BalanceService {
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;

    public BalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
    }

    public boolean hasEnoughCrypto(int userId, String cryptoSymbol, BigDecimal newOrderAmount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) return false;

        BigDecimal sells = calculateSells(userId, cryptoSymbol);
        BigDecimal availableCrypto = wallet.getCryptoBalance().get(cryptoSymbol);
        return availableCrypto != null && availableCrypto.compareTo(sells.add(newOrderAmount)) >= 0;
    }

    private BigDecimal calculateSells(int userId, String cryptoSymbol) {
        List<Order> orders = orderRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;

        for (Order order : orders) {
            if (order instanceof SellOrder && order.getUserId() == userId && order.getCryptoSymbol().equals(cryptoSymbol)) {
                total = total.add(order.getAmount());
            }
        }
        return total;
    }

    public boolean hasEnoughFiat(int userId, BigDecimal newOrderCost) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) return false;

        BigDecimal purchases = calculatePurchases(userId);
        BigDecimal availableFiat = wallet.getFiatBalance();
        return availableFiat != null && availableFiat.compareTo(purchases.add(newOrderCost)) >= 0;
    }

    private BigDecimal calculatePurchases(int userId) {
        List<Order> orders = orderRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;

        for (Order order : orders) {
            if (order instanceof BuyOrder && order.getUserId() == userId) {
                BuyOrder buyOrder = (BuyOrder) order;
                total = total.add(buyOrder.getAmount().multiply(buyOrder.getMaxPrice()));
            }
        }
        return total;
    }
}
