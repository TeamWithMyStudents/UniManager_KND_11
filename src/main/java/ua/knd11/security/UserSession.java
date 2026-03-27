package ua.knd11.security;

import ua.knd11.model.User;

public class UserSession {
    private static User currentUser = null;
    private static final Object lock = new Object();

    public static void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (isAuthenticated()) {
            throw new IllegalArgumentException("User is already logged in");
        }
        synchronized (lock) {
            currentUser = user;
        }
    }

    public static void logout() {
        synchronized (lock) {
            currentUser = null;
        }
    }

    public static User getCurrentUser() {
        synchronized (lock) {
            return currentUser;
        }
    }

    public static boolean isAuthenticated() {
        synchronized (lock) {
            return currentUser != null;
        }
    }
}
