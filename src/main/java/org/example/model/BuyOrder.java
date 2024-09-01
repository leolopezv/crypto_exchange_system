package org.example.model;

import java.math.BigDecimal;

public class BuyOrder extends Order {
    private final BigDecimal maxPrice;

    public BuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        super(userId, cryptoSymbol, amount);
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    @Override
    public boolean matches(Order otherOrder) {
        if (otherOrder instanceof SellOrder) {
            SellOrder sellOrder = (SellOrder) otherOrder;
            return this.cryptoSymbol.equals(sellOrder.getCryptoSymbol())
                    && this.amount.compareTo(sellOrder.getAmount()) == 0
                    && this.maxPrice.compareTo(sellOrder.getMinPrice()) >= 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BuyOrder{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", cryptoSymbol='" + cryptoSymbol + '\'' +
                ", amount=" + amount +
                ", maxPrice=" + maxPrice +
                ", date=" + date +
                '}';
    }
}