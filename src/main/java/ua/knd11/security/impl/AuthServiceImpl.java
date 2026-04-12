package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;

import java.util.ArrayList;

import static ua.knd11.util.FieldValidator.makeProtectedPassword;

public class AuthServiceImpl implements AuthService {
    // TODO: Нужно привязать к дб +
    //  делать ретурн к меню если дубликат имейла происходит что бы из-за дб код не дропался
    private final static ArrayList<User> registeredUsers = new ArrayList<>();


    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    public User login(String email, String value) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(value);

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

        String userPasswordGuess = makeProtectedPassword(value);
        if (foundUser == null || !foundUser.getPassword().equals(userPasswordGuess)) {
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

    public void removeUser(int id) throws IllegalArgumentException {
        if (registeredUsers.removeIf(user -> user.getId() == id))
            throw new IllegalArgumentException("User with this id not found");
    }
}
