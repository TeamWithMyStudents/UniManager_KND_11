package ua.knd11.security;

import ua.knd11.model.User;

/**
 * Service interface for user authentication and registration.
 */
public interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param user the user to register
     */
    void registration(User user);

    /**
     * Unregisters/removes a user from the system.
     *
     * @param user the user to unregister
     */
    void unregister(User user);

    /**
     * Authenticates a user with email and password.
     *
     * @param email    user's email address
     * @param password user's password
     * @return authenticated user or null if authentication fails
     */
    User login(String email, String password);
}
