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
     * Sets the global current user session to the provided user, replacing any existing session in a thread-safe manner.
     *
     * @param user the user to set as the current session; must not be null
     * @throws NullPointerException if {@code user} is null
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
     * Retrieves the currently logged-in user.
     *
     * @return the currently logged-in User, or `null` if no user is authenticated
     */
    public static User getCurrentUser() {
        synchronized (lock) {
            return currentUser;
        }
    }

    /**
     * Checks whether a user session is active.
     *
     * @return true if a user is currently logged in, false otherwise
     */
    public static boolean isAuthenticated() {
        synchronized (lock) {
            return currentUser != null;
        }
    }
}
