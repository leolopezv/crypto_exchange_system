package org.example.service.iService;

import java.math.BigDecimal;

public interface ITransferService {
    void transferCrypto(int fromUserId, int toUserId, String cryptoSymbol, BigDecimal amount);
    void transferFiat(int fromUserId, int toUserId, BigDecimal amount);
}