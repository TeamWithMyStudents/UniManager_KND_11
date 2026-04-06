package ua.knd11.security;

import ua.knd11.model.User;

import java.io.IOException;

/**
 * Service interface for user authentication and registration.
 */
public interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param user the user to register
     * @throws IllegalArgumentException if user is null or email already exists
     * @throws IOException if an I/O error occurs during user persistence
     */
    void registration(User user) throws IllegalArgumentException, IOException;

    /**
     * Authenticates a user with email and password.
     *
     * @param email    user's email address
     * @param password user's password
     * @return authenticated user or null if authentication fails
     */

    /**
     * Remove user from registered users list.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean removeUser(int id);

    User login(String email, String password);
}
