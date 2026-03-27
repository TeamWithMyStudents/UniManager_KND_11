package ua.knd11.security;

import ua.knd11.model.User;

public class UserSession {
    private static User currentUser = null;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuthenticated() {
        return currentUser != null;
    }
}
