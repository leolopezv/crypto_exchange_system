package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class WalletRepositoryInMemory implements WalletRepository {

    private Map<Integer, Wallet> walletLocation = new HashMap<>();

    @Override
    public void save(Wallet wallet) {
        walletLocation.put(wallet.getUserId(), wallet);
        System.out.println("Wallet saved for userId: " + wallet.getUserId());
    }

    @Override
    public Wallet findByUserId(int userId) {
        System.out.println("Searching wallet with userId: " + userId);
        return walletLocation.get(userId);
    }
}
