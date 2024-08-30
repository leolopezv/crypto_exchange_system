package org.example.model;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Order {
    protected int orderId;
    protected int userId;
    protected String cryptoSymbol;
    protected BigDecimal amount;
    protected BigDecimal price;
    protected Date date;

    public Order(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal price) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.amount = amount;
        this.price = price;
        this.date = new Date(); // Automatically set the order date to current date
    }

    public int getOrderId() {
        return orderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public abstract boolean matches(Order otherOrder);

    public abstract void executeOrder();
}
