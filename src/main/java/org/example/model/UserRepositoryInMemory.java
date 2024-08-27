package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {
    /*
    I am storing the data on memory, to avoid using .txt files or a database connection
     */
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
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}