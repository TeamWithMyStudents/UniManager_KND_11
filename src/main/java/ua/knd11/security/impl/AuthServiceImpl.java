package ua.knd11.security.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

/**
 * Implementation of the {@link AuthService} interface.
 * Handles user registration, authentication (login), and user removal
 * by coordinating between the model layer, session management, and database persistence.
 */
public class AuthServiceImpl implements AuthService {
    /**
     * Authenticates a user based on email and password.
     * Compares provided credentials against users in the database and the system SuperUser.
     *
     * @param email the user's email address
     * @param value the user's password
     */
    public void login(String email, String value) {
        try {
            FieldValidator.validateEmail(email);
            FieldValidator.validatePassword(value);
        } catch (IllegalArgumentException e) {
            System.err.println("Provided email or password is invalid");
            return;
        }

        if (UserSession.isAuthenticated()) {
            System.err.println("User is already logged in");
            return;
        }

        User superUser = UserSession.getSuperUser();
        if (superUser.getEmail().equalsIgnoreCase(email)) {
            if (superUser.getPassword().equals(value)) {
                UserSession.login(superUser);
                System.out.println("Login successful. Welcome, " + superUser.getName() + "!");
                return;
            }
        }

        User user = findUserByEmail(email);
        if (user != null && FieldValidator.verifyPassword(value, user.getPassword(), user.getSalt())) {
            UserSession.login(user);
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
            return;
        }
        System.err.println("Incorrect email or password");
    }

    /**
     * Helper method to find a user in the database by their email address.
     * The search priority is Teachers first, then Students.
     *
     * @param email the email address to search for (assumed to be already validated by caller)
     * @return the found {@link User} (Teacher or Student), or null if no match is found
     */
    private User findUserByEmail(String email) {
        Teacher teacher = SQLActions.getTeacherByEmail(email);
        if (teacher != null) return teacher;
        return SQLActions.getStudentByEmail(email);
    }
}