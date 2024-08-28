package org.example.controller;

import org.example.model.Wallet;
import org.example.service.WalletService;
import org.example.view.WalletView;
import java.math.BigDecimal;

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

        if (wallet == null) {
            walletView.showError("Wallet not found for userId: " + userId);
            return;
        }

        walletView.showWalletBalance(wallet.getFiatBalance(), wallet.getCryptocurrencyBalance());
    }

    public void buyReserveCrypto(int userId) {
        String cryptoSymbol = walletView.getCryptoSymbol();
        BigDecimal amount = walletView.getCryptoAmount();
        String result = walletService.buyReserveCrypto(userId, cryptoSymbol, amount);
        walletView.showSuccess(result);
        walletService.getExchange().showCryptoStock();
    }
}
