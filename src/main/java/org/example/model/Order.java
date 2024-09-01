package org.example.model;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Order {
    protected int orderId;
    protected int userId;
    protected String cryptoSymbol;
    protected BigDecimal amount;
    protected Date date;

    public Order(int userId, String cryptoSymbol, BigDecimal amount) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.amount = amount;
        this.date = new Date();
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public abstract boolean matches(Order otherOrder);

}
