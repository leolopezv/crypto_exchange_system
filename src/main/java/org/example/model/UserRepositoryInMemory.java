package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {
    private Map<Integer, User> users = new HashMap<>();
    private int currentId = 1;

    // Save the user in the memory
    @Override
    public void save(User user) {
        user.setUserId(currentId);
        users.put(currentId, user);
        currentId++;
    }

    // The following methods will help me validate the user's credentials

    @Override
    public User findById(int userId) {
        return users.get(userId);
    }

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