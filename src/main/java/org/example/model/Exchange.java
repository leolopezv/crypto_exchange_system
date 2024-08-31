package org.example.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exchange {
    private static Exchange instance;
    private Map<String, Crypto> cryptoMap;
    private Map<String, BigDecimal> cryptoReserves;

    public Exchange() {
        this.cryptoMap = new HashMap<>();
        this.cryptoReserves = new HashMap<>();
        initializeMarket();
    }

    public static Exchange getInstance() {
        if (instance == null) {
            instance = new Exchange();
        }
        return instance;
    }

    private void initializeMarket() {
        addCryptoToMarket(new Bitcoin(), BigDecimal.valueOf(100));
        addCryptoToMarket(new Ethereum(), BigDecimal.valueOf(500));
    }

    private void addCryptoToMarket(Crypto crypto, BigDecimal reserve) {
        cryptoMap.put(crypto.getSymbol(), crypto);
        cryptoReserves.put(crypto.getSymbol(), reserve);
    }

    public Crypto getCryptoBySymbol(String symbol) {
        return cryptoMap.get(symbol);
    }

    public BigDecimal getCryptoStock(String symbol) {
        return cryptoReserves.getOrDefault(symbol, BigDecimal.ZERO);
    }

    public void reduceCryptoStock(String symbol, BigDecimal quantity) {
        BigDecimal currentStock = cryptoReserves.get(symbol);
        cryptoReserves.put(symbol, currentStock.subtract(quantity));
    }

    public void showCryptoStock() {
        System.out.println("Current Exchange Stock:");
        for (Map.Entry<String, BigDecimal> entry : cryptoReserves.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public List<String> getAvailableCryptoSymbols() {
        return List.copyOf(cryptoMap.keySet());
    }
}