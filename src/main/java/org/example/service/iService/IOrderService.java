package org.example.service.iService;

import java.math.BigDecimal;

public interface IOrderService {
    void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice);
    void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice);
    void showPastTransactions(int userId);
}
