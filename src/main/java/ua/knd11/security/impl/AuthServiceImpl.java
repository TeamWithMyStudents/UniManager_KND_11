package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.UserFileHandler;

import java.util.ArrayList;

/**
 * Implementation of the AuthService interface for user registration and authentication.
 * Uses UserFileHandler to persist user data and manages user sessions.
 */
public class AuthServiceImpl implements AuthService {
    private final static ArrayList<User> registeredUsers = new ArrayList<>();

    /**
     * Creates a new AuthServiceImpl and initializes the user repository.
     * Loads existing users from persistent storage on first instantiation.
     */
    public AuthServiceImpl() {
        if (registeredUsers.isEmpty()) registeredUsers.addAll(UserFileHandler.getSavedList());
    }

    /**
     * Registers a new user in the system.
     * Validates the user data, checks for duplicate emails, and persists the user.
     *
     * @param user the user to be registered
     * @throws IllegalArgumentException if the user is null or email already exists
     */
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

    /**
     * Authenticates a user with email and password.
     * Validates credentials and creates a user session upon successful authentication.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return the authenticated user object
     * @throws IllegalArgumentException if user is already logged in or credentials are invalid
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

    /**
     * Gets a copy of the registered users list.
     *
     * @return a new ArrayList containing all registered users
     */
    @SuppressWarnings("unused")
    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }

    /**
     * Removes a user from the registered users list by ID.
     *
     * @param id the unique identifier of the user to remove
     * @return true if the user was removed, false if not found
     */
    public boolean removeUser(int id) {
        return registeredUsers.removeIf(user -> user.getId() == id);
    }
}
