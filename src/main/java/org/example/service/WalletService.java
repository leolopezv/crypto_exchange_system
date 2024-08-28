package org.example.service;

import org.example.model.Crypto;
import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.model.WalletRepository;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;
    private Exchange exchange;

    public WalletService(WalletRepository walletRepository, Exchange exchange) {
        this.walletRepository = walletRepository;
        this.exchange = exchange;
    }

    public Wallet getWalletByUserId(int userId) {
        Wallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            System.out.println("Wallet is null for userId: " + userId);
        }

        return wallet;
    }

    public void depositFiat(int userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            System.out.println("Wallet not found for user ID: " + userId);
            return;
        }
        wallet.addFiat(amount);
        walletRepository.save(wallet);
    }

    public String buyReserveCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            return "Wallet not found for user ID: " + userId;
        }

        Crypto crypto = exchange.getCryptoBySymbol(cryptoSymbol);
        if (crypto == null) {
            return "Invalid cryptocurrency selected.";
        }

        BigDecimal totalCost = crypto.getMarketPrice().multiply(amount);
        if (wallet.getFiatBalance().compareTo(totalCost) >= 0) {
            int availableStock = exchange.getCryptoStock(cryptoSymbol);
            if (availableStock < amount.intValue()) {
                return "Insufficient crypto stock in the exchange.";
            }

            wallet.deductFiat(totalCost);
            wallet.addCrypto(crypto, amount);
            exchange.reduceCryptoStock(cryptoSymbol, amount.intValue());
            walletRepository.save(wallet);

            return "Purchase successful! You bought " + amount + " " + crypto.getName();
        } else {
            return "Insufficient funds to complete the purchase.";
        }
    }

    public Exchange getExchange() {
        return exchange;
    }


    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }

}
