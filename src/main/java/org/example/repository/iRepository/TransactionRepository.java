package org.example.repository.iRepository;

import org.example.model.Transaction;
import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByUserId(int userId);
}