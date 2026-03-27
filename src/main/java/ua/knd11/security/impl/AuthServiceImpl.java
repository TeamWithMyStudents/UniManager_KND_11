package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        for (User registered : registeredUsers) {
            if (registered.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new IllegalArgumentException("User with this email already exists");
            }
        }
        String simpleHash = String.valueOf(user.getPassword().hashCode());
        user.setPassword(simpleHash);
        registeredUsers.add(user);
    }

    public User login(String email, String password) {
        String inputHash = String.valueOf(password.hashCode());

        for (User user : registeredUsers) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(inputHash)) {
                return user;
            }
        }

        throw new IllegalArgumentException("Incorrect email or password");
    }


    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
