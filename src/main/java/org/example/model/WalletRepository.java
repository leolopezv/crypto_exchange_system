package org.example.model;

public interface WalletRepository {
    void save(Wallet wallet);
    Wallet findByUserId(int userId);
}
