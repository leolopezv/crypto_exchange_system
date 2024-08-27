package org.example.service;

import org.example.model.Wallet;
import org.example.model.WalletRepository;

import java.math.BigDecimal;

public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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
