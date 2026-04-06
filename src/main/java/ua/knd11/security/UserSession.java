package ua.knd11.security;

import ua.knd11.model.User;

import java.util.Objects;

/**
 * Manages user session state and authentication status.
 * Provides thread-safe methods for login, logout, and session tracking.
 * Only one user can be logged in at a time.
 */
public class UserSession {
    private static final Object lock = new Object();
    private static User currentUser = null;

    /**
     * Logs in the specified user and creates a session.
     * Only one user can be logged in at a time.
     *
     * @param user the user to log in
     * @throws IllegalArgumentException if the user is null
     */
    public static void login(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        synchronized (lock) {
            currentUser = user;
        }
    }

    /**
     * Logs out the current user and terminates the session.
     *
     * @throws IllegalArgumentException if no user is currently logged in
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
     * Gets the currently logged-in user.
     *
     * @return the current user, or null if no user is logged in
     */
    public static User getCurrentUser() {
        synchronized (lock) {
            return currentUser;
        }
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isAuthenticated() {
        synchronized (lock) {
            return currentUser != null;
        }
    }
}
