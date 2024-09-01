package org.example.service;

import org.example.model.User;
import org.example.repository.iRepository.UserRepository;
import org.example.model.Wallet;
import org.example.repository.iRepository.WalletRepository;

public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public User registerUser(String name, String email, String password) {
        if (userExists(email)) return null;
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        Wallet newWallet = new Wallet(newUser.getUserId());
        walletRepository.save(newWallet);
        newUser.setWallet(newWallet);
        return newUser;
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
