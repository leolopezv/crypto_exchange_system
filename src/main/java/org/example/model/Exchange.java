package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exchange {
    private Map<String, Crypto> cryptoMap;
    private Map<String, Integer> cryptoReserves;

    public Exchange() {
        this.cryptoMap = new HashMap<>();
        this.cryptoReserves = new HashMap<>();
        initializeMarket();
    }

    private void initializeMarket() {
        addCryptoToMarket(new Bitcoin(), 100);
        addCryptoToMarket(new Ethereum(), 500);
    }

    private void addCryptoToMarket(Crypto crypto, int reserve) {
        cryptoMap.put(crypto.getSymbol(), crypto);
        cryptoReserves.put(crypto.getSymbol(), reserve);
    }

    public Crypto getCryptoBySymbol(String symbol) {
        return cryptoMap.get(symbol);
    }

    public int getCryptoStock(String symbol) {
        return cryptoReserves.getOrDefault(symbol, 0);
    }

    public void reduceCryptoStock(String symbol, int quantity) {
        int currentStock = cryptoReserves.get(symbol);
        cryptoReserves.put(symbol, currentStock - quantity);
    }

    public void showCryptoStock() {
        System.out.println("Current Exchange Stock:");
        for (Map.Entry<String, Integer> entry : cryptoReserves.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public List<String> getAvailableCryptoSymbols() {
        return List.copyOf(cryptoMap.keySet());
    }
}