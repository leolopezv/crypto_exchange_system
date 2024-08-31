package org.example.service;

import org.example.model.Wallet;
import org.example.repository.iRepository.WalletRepository;

import java.math.BigDecimal;

public class BalanceService {
    private final WalletRepository walletRepository;

    public BalanceService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public boolean hasSufficientCrypto(int userId, String cryptoSymbol, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet != null && wallet.getCryptoBalance().get(cryptoSymbol).compareTo(amount) >= 0;
    }

    public boolean hasSufficientFiat(int userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet != null && wallet.getFiatBalance().compareTo(amount) >= 0;
    }
}
