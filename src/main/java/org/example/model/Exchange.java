package org.example.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Exchange {
    private static Exchange instance;
    private Map<String, Crypto> cryptoMap;
    private Map<String, BigDecimal> cryptoReserves;
    private final Random random = new Random();

    public Exchange() {
        this.cryptoMap = new HashMap<>();
        this.cryptoReserves = new HashMap<>();
        initializeMarket();
        startPriceFluctuation();
    }

    public static Exchange getInstance() {
        if (instance == null) {
            instance = new Exchange();
        }
        return instance;
    }

    private void initializeMarket() {
        addCryptoToMarket(new Bitcoin(), BigDecimal.valueOf(100), BigDecimal.valueOf(50000));
        addCryptoToMarket(new Ethereum(), BigDecimal.valueOf(500), BigDecimal.valueOf(3000));
    }

    private void addCryptoToMarket(Crypto crypto, BigDecimal reserve, BigDecimal initialPrice) {
        cryptoMap.put(crypto.getSymbol(), crypto);
        cryptoReserves.put(crypto.getSymbol(), reserve);
        crypto.setMarketPrice(initialPrice);
    }

    public void reduceCryptoStock(String symbol, BigDecimal quantity) {
        BigDecimal currentStock = cryptoReserves.get(symbol);
        cryptoReserves.put(symbol, currentStock.subtract(quantity));
    }

    public List<String> getAllCryptoSymbols() {
        return List.copyOf(cryptoMap.keySet());
    }

    public Crypto getCryptoBySymbol(String symbol) {
        return cryptoMap.get(symbol);
    }

    public BigDecimal getCryptoStock(String symbol) {
        return cryptoReserves.getOrDefault(symbol, BigDecimal.ZERO);
    }

    public void showCryptoStock() {
        System.out.println("Current Exchange Stock:");
        for (Map.Entry<String, BigDecimal> entry : cryptoReserves.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void startPriceFluctuation() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::fluctuatePrices, 0, 1, TimeUnit.MINUTES);
    }

    private void fluctuatePrices() {
        for (Crypto crypto : cryptoMap.values()) {
            BigDecimal currentPrice = crypto.getMarketPrice();
            BigDecimal fluctuation = currentPrice.multiply(BigDecimal.valueOf((random.nextDouble() - 0.5) / 100));
            fluctuation = fluctuation.setScale(2, RoundingMode.HALF_UP);
            crypto.setMarketPrice(currentPrice.add(fluctuation));
        }
    }
}