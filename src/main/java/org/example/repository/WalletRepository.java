package org.example.repository;

import org.example.model.Wallet;

public interface WalletRepository {
    void save(Wallet wallet);
    Wallet findByUserId(int userId);
}
