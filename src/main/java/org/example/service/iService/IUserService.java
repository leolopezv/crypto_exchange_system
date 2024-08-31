package org.example.service.iService;

import org.example.model.User;

public interface IUserService {
    User registerUser(String name, String email, String password);
    User authenticateUser(String email, String password);
}