package org.example.model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Wallet wallet;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet(userId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}