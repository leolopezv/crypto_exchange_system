package org.example.model;

import java.math.BigDecimal;

public class Crypto {
    private String symbol;
    private String name;
    private BigDecimal marketPrice;

    public Crypto(String symbol, String name, BigDecimal marketPrice) {
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ") - on the Market is worth $" + marketPrice;
    }
}