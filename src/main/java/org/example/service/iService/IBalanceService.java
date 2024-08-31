package org.example.service.iService;


import java.math.BigDecimal;

public interface IBalanceService {
    boolean hasSufficientCrypto(int userId, String cryptoSymbol, BigDecimal newOrderAmount);
    boolean hasSufficientFiat(int userId, BigDecimal newOrderCost);
}