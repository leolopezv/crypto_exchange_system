package org.example.service;

import org.example.model.BuyOrder;
import org.example.model.Order;
import org.example.model.SellOrder;
import org.example.model.Wallet;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.iService.IBalanceService;

import java.math.BigDecimal;
import java.util.List;

public class BalanceService implements IBalanceService {
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;

    public BalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean hasSufficientCrypto(int userId, String cryptoSymbol, BigDecimal newOrderAmount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) return false;

        BigDecimal totalSellOrders = calculateTotalSellOrders(userId, cryptoSymbol);
        BigDecimal availableBalance = wallet.getCryptoBalance().get(cryptoSymbol);
        return availableBalance != null && availableBalance.compareTo(totalSellOrders.add(newOrderAmount)) >= 0;
    }

    private BigDecimal calculateTotalSellOrders(int userId, String cryptoSymbol) {
        List<Order> orders = orderRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;

        for (Order order : orders) {
            if (order instanceof SellOrder && order.getUserId() == userId && order.getCryptoSymbol().equals(cryptoSymbol)) {
                total = total.add(order.getAmount());
            }
        }
        return total;
    }

    @Override
    public boolean hasSufficientFiat(int userId, BigDecimal newOrderCost) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) return false;

        BigDecimal totalBuyOrderCost = calculateTotalBuyOrderCost(userId);
        BigDecimal availableFiatBalance = wallet.getFiatBalance();
        return availableFiatBalance != null && availableFiatBalance.compareTo(totalBuyOrderCost.add(newOrderCost)) >= 0;
    }

    private BigDecimal calculateTotalBuyOrderCost(int userId) {
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
