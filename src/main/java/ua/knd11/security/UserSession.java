package ua.knd11.security;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import ua.knd11.model.User;
import ua.knd11.util.FieldValidator;

import java.util.Objects;

/**
 * Manages the current authentication state of the application.
 * Provides thread-safe methods to handle user login, logout, and session-based access control.
 * Utilizes a static context to track the active {@link User}.
 */
public class UserSession {

    private final static String SUPER_USER_EMAIL;

    private final static String SUPER_USER_PASSWORD;

    static {
        try {
            FieldValidator.validateEmail(Dotenv.load().get("SUPER_USER_EMAIL"));
            SUPER_USER_EMAIL = Dotenv.load().get("SUPER_USER_EMAIL");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e + "SUPER_USER_EMAIL is not valid");
        }
    }

    static {
        try {
            FieldValidator.validatePassword(Dotenv.load().get("SUPER_USER_PASSWORD"));
            SUPER_USER_PASSWORD = Dotenv.load().get("SUPER_USER_PASSWORD");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e + "SUPER_USER_PASSWORD is not valid");
        }
    }

    /**
     * A predefined administrative user with full system privileges.
     * Initialized as an anonymous subclass of the abstract User.
     */
    @Getter
    private static final User superUser = new User("admin", "admin", SUPER_USER_EMAIL, SUPER_USER_PASSWORD) {
    };
    /**
     * Internal lock object used for thread synchronization
     */
    private static final Object lock = new Object();
    /**
     * The currently authenticated user; null if no session is active
     */
    private static User currentUser = null;


    /**
     * Establishes a session for the provided user.
     *
     * @param user the user instance to log in (cannot be null)
     * @throws NullPointerException if the provided user is null
     */
    public static void login(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        synchronized (lock) {
            currentUser = user;
        }
    }

    /**
     * Terminates the current user session.
     *
     * @throws IllegalArgumentException if no user is currently authenticated
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
     * Retrieves the user currently associated with the session.
     *
     * @return the active {@link User}, or null if not logged in
     */
    public static User getCurrentUser() {
        synchronized (lock) {
            return currentUser;
        }
    }

    /**
     * Checks whether a user is currently logged into the system.
     *
     * @return true if a session is active, false otherwise
     */
    public static boolean isAuthenticated() {
        synchronized (lock) {
            return currentUser != null;
        }
    }

    /**
     * Verifies if the current user has administrative (SuperUser) access.
     * Prints an "Access denied" message if the user is not the admin.
     *
     * @return false if access is denied (user is not superUser), true if access is granted
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkAccess() {
        User current = UserSession.getCurrentUser();
        if (current.equals(superUser)) {
            return true;
        }
        System.err.println(" Access denied!");
        return false;
    }
}