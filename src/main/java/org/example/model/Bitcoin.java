package org.example.model;

import java.math.BigDecimal;

public class Bitcoin extends Crypto {
    public Bitcoin() {
        super("BTC", "Bitcoin", new BigDecimal("50000"));
    }
}
