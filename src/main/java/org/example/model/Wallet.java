package org.example.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private int userId;
    private BigDecimal fiatBalance;
    private Map<String, BigDecimal> cryptoBalance;

    public Wallet(int userId) {
        this.userId = userId;
        this.fiatBalance = BigDecimal.ZERO;
        this.cryptoBalance = new HashMap<>();
        initializeCryptoBalances();
    }

    private void initializeCryptoBalances() {
        cryptoBalance.put("BTC", BigDecimal.ZERO);
        cryptoBalance.put("ETH", BigDecimal.ZERO);
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getFiatBalance() {
        return fiatBalance;
    }

    public void addFiat(BigDecimal amount) {
        fiatBalance = fiatBalance.add(amount);
    }

    public void deductFiat(BigDecimal amount) {
        fiatBalance = fiatBalance.subtract(amount);
    }

    public Map<String, BigDecimal> getCryptoBalance() {
        return cryptoBalance;
    }

    public void addCrypto(Crypto crypto, BigDecimal amount) {
        BigDecimal currentAmount = cryptoBalance.get(crypto.getSymbol());
        cryptoBalance.put(crypto.getSymbol(), currentAmount.add(amount));
    }

    public void deductCrypto(Crypto crypto, BigDecimal amount) {
        BigDecimal currentAmount = cryptoBalance.get(crypto.getSymbol());
        cryptoBalance.put(crypto.getSymbol(), currentAmount.subtract(amount));
    }
}