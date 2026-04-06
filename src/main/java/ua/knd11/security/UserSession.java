package ua.knd11.security;

import ua.knd11.model.User;

import java.util.Objects;

/**
 * Manages user session state with thread-safe operations.
 */
public class UserSession {
    private static final Object lock = new Object();
    private static User currentUser = null;

    /**
     * Logs in a user and sets current session.
     *
     * @param user the user to log in
     * @throws NullPointerException if user is null
     */
    public static void login(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        synchronized (lock) {
            currentUser = user;
        }
    }

    /**
     * Logs out the current user and clears session.
     *
     * @throws IllegalArgumentException if no user is logged in
     */
    public static void logout() {
        synchronized (lock) {
            if (!isAuthenticated()) {
                throw new IllegalArgumentException("User is not logged in");
            }
            System.out.println("Logout successful");
            currentUser = null;
        }
    }

    /**
     * Gets current user.
     *
     * @return the currently logged-in user, or null if none
     */
    public static User getCurrentUser() {
        synchronized (lock) {
            return currentUser;
        }
    }

    /**
     * Is authenticated boolean.
     *
     * @return true if a user is currently logged in
     */
    public static boolean isAuthenticated() {
        synchronized (lock) {
            return currentUser != null;
        }
    }
}
