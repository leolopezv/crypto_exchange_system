package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private Map<Integer, User> users = new HashMap<>();
    private int currentId = 1;

    @Override
    public void save(User user) {
        user.setUserId(currentId);
        users.put(currentId, user);
        currentId++;
    }

    @Override
    public User findById(int userId) {
        return users.get(userId);
    }

    // Filter methods for checking if a user exists by name or email

    @Override
    public User findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
