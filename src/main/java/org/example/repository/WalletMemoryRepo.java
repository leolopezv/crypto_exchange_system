package org.example.repository;

import org.example.model.Wallet;
import org.example.repository.iRepository.WalletRepository;

import java.util.HashMap;
import java.util.Map;

public class WalletMemoryRepo implements WalletRepository {
    private Map<Integer, Wallet> walletLocation = new HashMap<>();

    @Override
    public void save(Wallet wallet) {
        walletLocation.put(wallet.getUserId(), wallet);
        //System.out.println("Wallet saved for userId: " + wallet.getUserId());
    }

    @Override
    public Wallet findByUserId(int userId) {
        return walletLocation.get(userId);
    }
}