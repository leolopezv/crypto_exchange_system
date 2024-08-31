package org.example.service.iService;

import org.example.model.Wallet;

import java.math.BigDecimal;

public interface IWalletService {
    Wallet getWalletByUserId(int userId);
    void depositFiat(int userId, BigDecimal amount);
    String buyReserveCrypto(int userId, String cryptoSymbol, BigDecimal amount);
    Wallet getWalletBalance(int userId);
}