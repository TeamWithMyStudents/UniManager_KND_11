package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        registeredUsers.add(user);
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
        if (foundUser == null) {
            throw new NoSuchElementException("User with this email no found");
        }

        if (foundUser.getPassword().equals(password)) {
            System.out.println("Login successful");
            UserSession.login(foundUser);
            return foundUser;
        } else throw new IllegalArgumentException("Incorrect password");
    }

    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
