package org.example.view;

import java.math.BigDecimal;
import java.util.Map;

public class WalletView extends ConsoleView {
    public BigDecimal getUserAmount() {
        while (true) {
            System.out.print("Enter your desired amount: ");
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

    public void showWalletBalance(BigDecimal fiatBalance, Map<String, BigDecimal> cryptoBalance) {
        System.out.println("Wallet fiat money balance: " + fiatBalance);
        System.out.println("Wallet cryptocurrency balances: BTC: " + cryptoBalance.get("BTC") + " | ETH: " + cryptoBalance.get("ETH"));
    }

    public String getCryptoSymbol() {
        System.out.print("Enter the symbol of the cryptocurrency you want to buy [BTC/ETH]: ");
        return scanner.next();
    }
}