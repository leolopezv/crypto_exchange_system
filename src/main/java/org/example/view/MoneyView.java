package org.example.view;

import org.example.model.Exchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MoneyView extends ConsoleView {
    public BigDecimal getUserAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
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
        System.out.println("Wallet fiat Balance [USD]: " + fiatBalance);
        cryptoBalance.forEach((crypto, balance) -> System.out.print("Balance [" + crypto + "]: " + balance + " "));
        System.out.println();
    }

    public String getCryptoSymbol(List<String> validSymbols) {
        while (true) {
            System.out.print("Enter a symbol " + validSymbols + ": ");
            String symbol = scanner.next().toUpperCase(Locale.ROOT);
            if (validSymbols.contains(symbol)) {
                return symbol;
            } else {
                showError("Invalid symbol. Enter one of these: " + validSymbols);
            }
        }
    }

    public BigDecimal getInfoAmount(Exchange exchange, String cryptoSymbol) {
        System.out.println(exchange.getCryptoBySymbol(cryptoSymbol));
        return getUserAmount("Enter the amount of crypto: ");
    }
}