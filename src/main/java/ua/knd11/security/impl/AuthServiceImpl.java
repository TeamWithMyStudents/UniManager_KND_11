package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.UserFileHandler;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private final static ArrayList<User> registeredUsers = new ArrayList<>();

    public AuthServiceImpl() {
        registeredUsers.addAll(UserFileHandler.getSavedList());
    }

    public void registration(User user) {
        if (user == null) throw new IllegalArgumentException("User must not be null");

        for (User existingUser : registeredUsers) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        registeredUsers.add(user);
        UserFileHandler.saveUsers(user);
    }

    public User login(String email, String password) {

        if (UserSession.isAuthenticated()) {
            throw new IllegalArgumentException("User is already logged in");
        }

        User foundUser = null;
        for (User user : registeredUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                foundUser = user;
                break;
            }
        }

        String passwordHash = String.valueOf(password.hashCode());
        if (foundUser == null || !foundUser.getPassword().equals(passwordHash)) {
            throw new IllegalArgumentException("User with this email not found or incorrect password");
        }
        UserSession.login(foundUser);
        System.out.println("Login successful");
        return foundUser;
    }

    @SuppressWarnings("unused")
    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
