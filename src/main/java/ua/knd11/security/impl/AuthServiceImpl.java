package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.Scanner;

/**
 * Implementation of the {@link AuthService} interface.
 * Handles user registration, authentication (login), and user removal
 * by coordinating between the model layer, session management, and database persistence.
 */
public class AuthServiceImpl implements AuthService {

    Scanner input = new Scanner(System.in);

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
            if (FieldValidator.verifyPassword(value, superUser.getPassword(), superUser.getSalt())) {
                UserSession.login(superUser);
                System.out.println("Login successful. Welcome, " + superUser.getName() + "!");
                return;
            }
        }

        User student = SQLActions.getStudentByEmail(email);
        User teacher = SQLActions.getTeacherByEmail(email);
        User user = null;

        if (student != null && teacher != null) {
            System.out.println("Provided email has two entries\n 1. Student\n 2. Teacher");
            switch (input.nextLine().trim()) {
                case "1" -> user = student;
                case "2" -> user = teacher;
            }
        } else if (student != null) {
            user = student;
        } else if (teacher != null) {
            user = teacher;
        }

        if (user != null && FieldValidator.verifyPassword(value, user.getPassword(), user.getSalt())) {
            UserSession.login(user);
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
            return;
        }
        System.err.println("Incorrect email or password");
    }
}