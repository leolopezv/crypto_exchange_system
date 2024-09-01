package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int userId;
    private String cryptoSymbol;
    private BigDecimal amount;
    private BigDecimal price;
    private String action; // "buy" or "sell"
    private LocalDateTime timestamp;

    public Transaction(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal price, String action, LocalDateTime timestamp) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.amount = amount;
        this.price = price;
        this.action = action;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userId=" + userId +
                ", cryptoSymbol='" + cryptoSymbol + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}