package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        registeredUsers.add(user);
    }

    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
