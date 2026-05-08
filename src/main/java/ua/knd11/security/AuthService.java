package ua.knd11.security;

import ua.knd11.model.User;

public interface AuthService {
    void registerUser(User user);

    void login(String email, String password) throws IllegalArgumentException;

    void removeStudent(int id);
    void removeTeacher(int id);
}
