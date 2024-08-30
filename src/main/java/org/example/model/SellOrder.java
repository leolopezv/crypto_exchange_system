package org.example.model;

import java.math.BigDecimal;

public class SellOrder extends Order {
    private BigDecimal minPrice;

    public SellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        super(userId, cryptoSymbol, amount);
        this.minPrice = minPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    @Override
    public boolean matches(Order otherOrder) {
        if (otherOrder instanceof BuyOrder) {
            BuyOrder buyOrder = (BuyOrder) otherOrder;
            return this.cryptoSymbol.equals(buyOrder.getCryptoSymbol())
                    && this.amount.compareTo(buyOrder.getAmount()) == 0
                    && this.minPrice.compareTo(buyOrder.getMaxPrice()) <= 0;
        }
        return false;
    }

    @Override
    public void executeOrder() {
        System.out.println("Executing sell order: " + this);
    }

    @Override
    public String toString() {
        return "SellOrder{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", cryptoSymbol='" + cryptoSymbol + '\'' +
                ", amount=" + amount +
                ", minPrice=" + minPrice +
                ", date=" + date +
                '}';
    }
}
