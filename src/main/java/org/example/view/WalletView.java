package org.example.view;

import java.math.BigDecimal;

public class WalletView extends ConsoleView {

    public BigDecimal getUserAmount() {
        System.out.print("Enter the amount you want to deposit to your fiat balance: ");
        return scanner.nextBigDecimal();
    }

    public void showWalletBalance(BigDecimal fiatBalance) {
        System.out.println("Wallet fiat money balance: " + fiatBalance);
    }
}
