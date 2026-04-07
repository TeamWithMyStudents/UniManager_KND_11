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
     * Populates the in-memory registered users list from persistent storage when it is empty.
     *
     * If the shared {@code registeredUsers} list contains no entries, loads saved users and adds them to it.
     */
    public AuthServiceImpl() {
        if (registeredUsers.isEmpty()) registeredUsers.addAll(UserFileHandler.getSavedList());
    }

    /**
         * Registers a new user: validates input, replaces the user's password with its hash,
         * persists the user to storage, and adds the user to the in-memory registry.
         *
         * @param user the user to register; must be non-null, must have an email not already present (case-insensitive), and will have its password replaced with the password's hash
         * @throws IllegalArgumentException if {@code user} is null or a user with the same email already exists
         * @throws IOException if persisting the user to storage fails
         */
    public void registration(User user) throws IllegalArgumentException, IOException {
        if (user == null) throw new IllegalArgumentException("User must not be null");

        for (User existingUser : registeredUsers) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        UserFileHandler.saveUsers(user);
        registeredUsers.add(user);
    }

    /**
     * Authenticate a user by email and password and establish a user session.
     *
     * @param email    the user's email address (case-insensitive)
     * @param password the user's plain-text password
     * @return the authenticated {@code User}
     * @throws IllegalArgumentException if a user is already logged in, no matching email is found, or the password is incorrect
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
     * Returns a defensive copy of the list of registered users.
     *
     * @return a new ArrayList containing all currently registered users
     */
    @SuppressWarnings("unused")
    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }

    /**
     * Removes the user with the specified id from the registered users list.
     *
     * @param id the id of the user to remove
     * @return `true` if a user with the given id was removed, `false` otherwise
     */
    public boolean removeUser(int id) {
        return registeredUsers.removeIf(user -> user.getId() == id);
    }
}
