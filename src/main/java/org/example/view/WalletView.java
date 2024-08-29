package org.example.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WalletView extends ConsoleView {
    public BigDecimal getUserAmount() {
        while (true) {
            System.out.print("Enter amount: ");
            try {
                BigDecimal amount = scanner.nextBigDecimal();
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    showError("Amount must be positive.");
                } else {
                    return amount;
                }
            } catch (Exception e) {
                scanner.nextLine();
                showError("Should be a valid number.");
            }
        }
    }

    public void showWalletBalance(BigDecimal fiatBalance, Map<String, BigDecimal> cryptoBalance) {
        System.out.println("Wallet fiat money balance: " + fiatBalance);
        cryptoBalance.forEach((crypto, balance) -> System.out.println(crypto + ": " + balance));
    }

    public String getCryptoSymbol(List<String> validSymbols) {
        while (true) {
            System.out.print("Enter a symbol " + validSymbols + ": ");
            String symbol = scanner.next();
            if (validSymbols.contains(symbol)) {
                return symbol;
            } else {
                showError("Invalid symbol. Enter one of these: " + validSymbols);
            }
        }
    }
}