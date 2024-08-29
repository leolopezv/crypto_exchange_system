package org.example.controller;

import org.example.model.Wallet;
import org.example.service.WalletService;
import org.example.view.WalletView;

import java.math.BigDecimal;
import java.util.List;

public class WalletController {
    private final WalletService walletService;
    private final WalletView walletView;

    public WalletController(WalletService walletService, WalletView walletView) {
        this.walletService = walletService;
        this.walletView = walletView;
    }

    public void depositMoney(int userId) {
        BigDecimal amount = walletView.getUserAmount();
        walletService.depositFiat(userId, amount);
        walletView.showSuccess("Deposit successful! New balance: " + walletService.getWalletBalance(userId).getFiatBalance());
    }

    public void viewWalletBalance(int userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        if (wallet != null) {
            walletView.showWalletBalance(wallet.getFiatBalance(), wallet.getCryptocurrencyBalance());
        } else {
            walletView.showError("Wallet not found. ID: " + userId);
        }
    }

    public void buyReserveCrypto(int userId) {
        List<String> validSymbols = walletService.getExchange().getAvailableCryptoSymbols();
        String cryptoSymbol = walletView.getCryptoSymbol(validSymbols);
        BigDecimal amount = walletView.getUserAmount();
        walletView.showMessage(walletService.buyReserveCrypto(userId, cryptoSymbol, amount));
        walletService.getExchange().showCryptoStock();
    }
}