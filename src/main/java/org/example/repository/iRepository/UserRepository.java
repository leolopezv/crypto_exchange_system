package org.example.repository.iRepository;

import org.example.model.User;

public interface UserRepository {
    void save(User user);
    User findById(int userId);
    User findByEmail(String email);
}