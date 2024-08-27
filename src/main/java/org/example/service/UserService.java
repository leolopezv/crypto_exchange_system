package org.example.service;

import org.example.model.User;
import org.example.model.UserRepository;
import org.example.model.Wallet;
import org.example.model.WalletRepository;

public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User registerUser(String name, String email, String password) {
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        Wallet newWallet = new Wallet(newUser.getUserId()); //new
        walletRepository.save(newWallet); //new
        newUser.setWallet(newWallet);
        return newUser;
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}