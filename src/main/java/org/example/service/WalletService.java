package org.example.service;

import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;
    private Exchange exchange;

    public WalletService(WalletRepository walletRepository, Exchange exchange) {
        this.walletRepository = walletRepository;
        this.exchange = exchange;
    }

    public Wallet getWalletByUserId(int userId) {
        return findWalletByUserId(userId);
    }

    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = findWalletByUserId(userId);
        if (wallet == null) return;
        wallet.addFiat(amount);
        walletRepository.save(wallet);
    }

    public boolean buyReserveCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = findWalletByUserId(userId);
        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
        if (wallet == null || crypto == null) return false;

        BigDecimal totalCost = crypto.getMarketPrice().multiply(amount);
        int availableStock = exchange.getCryptoStock(cryptoSymbol);
        if (wallet.getFiatBalance().compareTo(totalCost) < 0 || availableStock < amount.intValue() ) return false;

        wallet.deductFiat(totalCost);
        wallet.addCrypto(crypto, amount);
        exchange.reduceCryptoStock(cryptoSymbol, amount.intValue());
        walletRepository.save(wallet);
        return true;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }

    private Wallet findWalletByUserId(int userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) System.out.println("Wallet not found for user ID: " + userId);
        return wallet;
    }
}