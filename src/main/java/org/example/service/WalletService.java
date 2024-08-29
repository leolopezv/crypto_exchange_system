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

    public Exchange getExchange() {
        return exchange;
    }

    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }
}