package ua.knd11.security;

public interface AuthService {
    // --Commented out by Inspection (10.05.2026 19:11):void registerUser(User user);
    void login(String email, String password) throws IllegalArgumentException;
}
