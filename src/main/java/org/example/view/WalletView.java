package org.example.view;

import java.math.BigDecimal;

public class WalletView extends ConsoleView {

    public BigDecimal getUserAmount() {
        while (true) {
            System.out.print("Enter the amount you want to deposit to your fiat balance: ");
            try {
                BigDecimal amount = scanner.nextBigDecimal();
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    showError("Invalid amount. Please enter a positive number.");
                } else {
                    return amount;
                }
            } catch (Exception e) {
                scanner.nextLine();
                showError("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void showWalletBalance(BigDecimal fiatBalance) {
        System.out.println("Wallet fiat money balance: " + fiatBalance);
    }
}
