package org.example.repository.iRepository;

import org.example.model.User;

public interface UserRepository {
    void save(User user);
    User findById(int userId);
    User findByName(String name);
    User findByEmail(String email);
}