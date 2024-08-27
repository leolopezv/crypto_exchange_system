package org.example.controller;

import org.example.model.Wallet;
import org.example.service.WalletService;
import org.example.view.WalletView;
import java.math.BigDecimal;
import java.util.InputMismatchException;

public class WalletController {
    private final WalletService walletService;
    private final WalletView walletView;

    public WalletController(WalletService walletService, WalletView walletView) {
        this.walletService = walletService;
        this.walletView = walletView;
    }

    public void depositMoney(int userId) {
        try {
            walletView.showMessage("Enter the amount to deposit: ");
            BigDecimal amount = walletView.getUserAmount();
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                walletService.depositFiat(userId, amount);
                walletView.showSuccess("Deposit successful! New balance: " + walletService.getWalletBalance(userId).getFiatBalance());
            } else {
                walletView.showError("Invalid amount. Please enter a positive value.");
            }
        } catch (InputMismatchException e) {
            walletView.showError("Invalid input. Please enter a valid number.");
        }
    }

    public void viewWalletBalance(int userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);

        if (wallet == null) {
            walletView.showError("Wallet not found for userId: " + userId);
            return;
        }

        walletView.showWalletBalance(wallet.getFiatBalance());
    }

}
