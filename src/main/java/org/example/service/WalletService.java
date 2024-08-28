package org.example.service;

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

    public Wallet getWalletBalance(int userId) {
        return walletRepository.findByUserId(userId);
    }

}
