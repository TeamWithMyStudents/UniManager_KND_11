package ua.knd11.security;

import ua.knd11.model.User;

import java.util.Objects;

public class UserSession { // TODO: Реализовать авторизацию при запуске кода + AuthServiceIMPL
    private static final Object lock = new Object();
    private static User currentUser = null;

    public static void login(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        synchronized (lock) {
            currentUser = user;
        }
    }

    public static void logout() {
        synchronized (lock) {
            if (!isAuthenticated()) {
                throw new IllegalArgumentException("User is not logged in");
            }
            System.out.println("Logout successful");
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
