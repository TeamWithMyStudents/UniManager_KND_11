package ua.knd11.security;

import ua.knd11.model.User;

/**
 * Authentication service interface for user registration and login operations.
 * Manages user authentication and session handling.
 */
public interface AuthService {
    /**
     * Registers a new user in the system.
     * Validates the user data and stores it for future authentication.
     *
     * @param user the user to be registered
     * @throws IllegalArgumentException if the user is null or email already exists
     */
    void registration(User user);

    /**
     * Authenticates a user with the provided email and password.
     * Creates a user session upon successful authentication.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return the authenticated user object
     * @throws IllegalArgumentException if the user is already logged in or credentials are invalid
     */
    User login(String email, String password);
}
