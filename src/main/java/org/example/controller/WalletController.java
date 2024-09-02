package org.example.controller;

import org.example.model.Exchange;
import org.example.model.Wallet;
import org.example.service.WalletService;
import org.example.service.exception.FailedTransactionEx;
import org.example.service.exception.InvalidCryptoEx;
import org.example.service.exception.InvalidWalletEx;
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
        try {
            walletService.depositFiat(userId, amount);
            moneyView.showSuccess("Deposit successful! New balance: " + walletService.getWalletByUser(userId).getFiatBalance());
        } catch (InvalidWalletEx e) {
            moneyView.showError(e.getMessage());
        }
    }

    public void viewWallet(int userId) {
        try {
            Wallet wallet = walletService.getWalletByUser(userId);
            moneyView.showWalletBalance(wallet.getFiatBalance(), wallet.getCryptoBalance());
        } catch (InvalidWalletEx e) {
            moneyView.showError(e.getMessage());
        }
    }

    public void buyFromExchange(int userId) {
        String cryptoSymbol = moneyView.getCryptoSymbol(exchange.getAllCryptoSymbols());
        BigDecimal amount = moneyView.getInfoAmount(exchange, cryptoSymbol);
        try {
            walletService.buyExchangeCrypto(userId, cryptoSymbol, amount);
            moneyView.showSuccess("Purchase successful! New balance: " + walletService.getWalletByUser(userId).getFiatBalance());
        } catch (FailedTransactionEx | InvalidWalletEx | InvalidCryptoEx e) {
            moneyView.showError(e.getMessage());
        } finally {
            exchange.showCryptoStock();
        }
    }
}