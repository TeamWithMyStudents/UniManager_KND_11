package ua.knd11.security;

public interface AuthService {
    // --Commented out by Inspection (10.05.2026 19:11):void registerUser(User user);

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @throws IllegalArgumentException if the email or password format is invalid
     */
    void login(String email, String password);
}
