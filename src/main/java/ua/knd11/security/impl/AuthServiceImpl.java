package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        registeredUsers.add(user);
    }

    public User login(String email, String password) {
        User foundUser = null;
        for (User user : registeredUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                foundUser = user;
                break;
            }
        }

        if (foundUser == null || !foundUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect email or password");
        }
        return foundUser;
    }

    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
