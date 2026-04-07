package ua.knd11.security;

import ua.knd11.model.User;

import java.io.IOException;

/**
 * Service interface for user authentication and registration.
 */
public interface AuthService {
    /**
 * Register a new user in the system.
 *
 * @param user the user to register
 * @throws IllegalArgumentException if `user` is null or a user with the same email already exists
 * @throws IOException if an I/O error occurs while persisting the user
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
 * Removes the user with the specified id from the registered users list.
 *
 * @param id the identifier of the user to remove
 * @return `true` if a user was removed, `false` if no user with the given id existed
 */
    public boolean removeUser(int id);

    /**
 * Authenticate a user using the provided email and password.
 *
 * @param email    the user's email address
 * @param password the user's password
 * @return the authenticated {@link User} when credentials are valid, {@code null} otherwise
 */
User login(String email, String password);
}
