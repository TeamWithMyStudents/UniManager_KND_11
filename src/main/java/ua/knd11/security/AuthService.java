package ua.knd11.security;

import ua.knd11.model.User;

public interface AuthService {
    void registerUser(User user);

    User login(String email, String password) throws IllegalArgumentException;

    void removeUser(int id) throws IllegalArgumentException;
}
