package org.example.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private int userId;
    private BigDecimal fiatBalance;
    //private Map<Cryptocurrency, BigDecimal> cryptocurrencyBalance;

    public Wallet(int userId) {
        this.userId = userId;
        this.fiatBalance = BigDecimal.ZERO;
        //this.cryptocurrencyBalance = new HashMap<>();
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
}
