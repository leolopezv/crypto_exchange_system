package org.example.controller;

import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.service.WalletService;
import org.example.view.MoneyView;

import java.math.BigDecimal;

public class WalletController {
    private final WalletService walletService;
    private final MoneyView moneyView;
    private final Exchange exchange = Exchange.getInstance();

    public WalletController(WalletService walletService, MoneyView moneyView) {
        this.walletService = walletService;
        this.moneyView = moneyView;
    }

    public void depositMoney(int userId) {
        BigDecimal amount = moneyView.getUserAmount("Enter the amount of fiat money you want to deposit: ");
        walletService.depositFiat(userId, amount);
        moneyView.showSuccess("Deposit successful! New balance: " + walletService.getWalletBalance(userId).getFiatBalance());
    }

    public void viewWallet(int userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        if (wallet != null) {
            moneyView.showWalletBalance(wallet.getFiatBalance(), wallet.getCryptoBalance());
        } else {
            moneyView.showError("Wallet not found. ID: " + userId);
        }
    }

    public void buyFromExchange(int userId) {
        String cryptoSymbol = moneyView.getCryptoSymbol(exchange.getAllCryptoSymbols());
        BigDecimal amount = moneyView.getInfoAmount(exchange, cryptoSymbol);
        moneyView.showMessage(walletService.buyExchangeCrypto(userId, cryptoSymbol, amount));
        exchange.showCryptoStock();
    }
}