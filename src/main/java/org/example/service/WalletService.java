package org.example.service;

import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;
    private final Exchange exchange;
    private OrderService orderService;

    public WalletService(WalletRepository walletRepository, Exchange exchange, OrderService orderService) {
        this.walletRepository = walletRepository;
        this.exchange = exchange;
        this.orderService = orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public Wallet getWalletByUserId(int userId) {
        return walletRepository.findByUserId(userId);
    }

    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        if (wallet != null) {
            wallet.addFiat(amount);
            walletRepository.save(wallet);
        }
    }

    public String buyReserveCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
        if (wallet == null || crypto == null) return "Wallet or crypto not found";

        BigDecimal totalCost = crypto.getMarketPrice().multiply(amount);
        BigDecimal availableStock = exchange.getCryptoStock(cryptoSymbol);
        if (wallet.getFiatBalance().compareTo(totalCost) < 0 || availableStock.compareTo(amount) < 0) return "Insufficient funds or stock";

        wallet.deductFiat(totalCost);
        wallet.addCrypto(crypto, amount);
        exchange.reduceCryptoStock(cryptoSymbol, amount);
        walletRepository.save(wallet);
        return "Purchase successful";
    }

    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        orderService.placeBuyOrder(userId, cryptoSymbol, amount, maxPrice);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        orderService.placeSellOrder(userId, cryptoSymbol, amount, minPrice);
    }

    public Exchange getExchange() {
        return exchange;
    }

    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }

    public boolean hasSufficientCrypto(int userId, String crypto, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        return wallet != null && wallet.getCryptoBalance().get(crypto).compareTo(amount) >= 0;
    }

    public void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount) {
        Wallet fromWallet = getWalletByUserId(fromUserId);
        Wallet toWallet = getWalletByUserId(toUserId);

        fromWallet.deductCrypto(exchange.getCryptoBySymbol(cryptoSymbol), amount);
        toWallet.addCrypto(exchange.getCryptoBySymbol(cryptoSymbol), amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    public void transferFiat(int fromUserId, int toUserId, BigDecimal amount) {
        Wallet fromWallet = getWalletByUserId(fromUserId);
        Wallet toWallet = getWalletByUserId(toUserId);

        fromWallet.deductFiat(amount);
        toWallet.addFiat(amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }
}
