package org.example.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private int userId;
    private BigDecimal fiatBalance;
    private Map<String, BigDecimal> cryptoBalance;

    public Wallet(int userId) {
        this.userId = userId;
        this.fiatBalance = BigDecimal.ZERO;
        this.cryptoBalance = new HashMap<>();
        this.cryptoBalance.put("BTC", BigDecimal.ZERO);
        this.cryptoBalance.put("ETH", BigDecimal.ZERO);
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getFiatBalance() {
        return fiatBalance;
    }

    public void addFiat(BigDecimal amount) {
        fiatBalance = fiatBalance.add(amount);
    }

    public void deductFiat(BigDecimal amount) {
        if (fiatBalance.compareTo(amount) < 0) {
            // throw new IllegalArgumentException("Insufficient funds to complete the transaction.");
        } else {
            fiatBalance = fiatBalance.subtract(amount);
        }
    }

    public Map<String, BigDecimal> getCryptocurrencyBalance() {
        return cryptoBalance;
    }

}
