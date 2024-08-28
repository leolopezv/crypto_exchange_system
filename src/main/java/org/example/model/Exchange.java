package org.example.model;

import java.util.HashMap;
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

    public Map<String, Crypto> getCryptoMap() {
        return cryptoMap;
    }

    public Map<String, Integer> getCryptoReserves() {
        return cryptoReserves;
    }
}