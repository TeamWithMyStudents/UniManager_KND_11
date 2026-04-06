package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.UserFileHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of AuthService for user authentication and registration.
 */
public class AuthServiceImpl implements AuthService {
    private final static ArrayList<User> registeredUsers = new ArrayList<>();

    /**
     * Initializes AuthService and loads existing users from file.
     */
    public AuthServiceImpl() {
        if (registeredUsers.isEmpty()) registeredUsers.addAll(UserFileHandler.getSavedList());
    }

    /**
     * Registers a new user in the system.
     * @param user the user to register
     * @throws IllegalArgumentException if user is null or email already exists
     */
    public void registration(User user) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User must not be null");

        for (User existingUser : registeredUsers) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        try {
            registeredUsers.add(user);
            UserFileHandler.saveUsers(user);
        } catch (IOException e) {
            registeredUsers.remove(user);
            throw new IllegalArgumentException("Failed to save user to file, user not registered");
        }
    }

    /**
     * Authenticates a user with email and password.
     * @param email user's email address
     * @param password user's password
     * @return authenticated user
     * @throws IllegalArgumentException if authentication fails or user already logged in
     */
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

    public void unregister(User user) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User must not be null");
        registeredUsers.remove(user);
    }

    /**
     * Gets registered users.
     *
     * @return the registered users
     */
    @SuppressWarnings("unused")
    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }

    /**
     * Remove user boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean removeUser(int id) {
        return registeredUsers.removeIf(user -> user.getId() == id);
    }
}
