package org.example.repository;

import org.example.model.Transaction;
import org.example.repository.iRepository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionMemoryRepo implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> findByUserId(int userId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) if (transaction.getUserId() == userId) result.add(transaction);
        return result;
    }
}