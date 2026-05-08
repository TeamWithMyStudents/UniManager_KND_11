package ua.knd11.security.impl;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

import static ua.knd11.util.FieldValidator.makeProtectedPassword;
import static ua.knd11.util.FieldValidator.validateId;

/**
 * Implementation of the {@link AuthService} interface.
 * Handles user registration, authentication (login), and user removal
 * by coordinating between the model layer, session management, and database persistence.
 */
public class AuthServiceImpl implements AuthService {
    /**
     * Registers a new user in the system.
     * Validates user fields and ensures the email is unique before persisting
     * to the database as either a Student or a Teacher.
     * @param user the user instance to be registered
     */
    public void registerUser(User user) {
        FieldValidator.validateAlphabeticString(user.getName(), "name");
        FieldValidator.validateAlphabeticString(user.getSurname(), "surname");
        FieldValidator.validateEmail(user.getEmail());
        FieldValidator.validatePassword(user.getPassword());

        if (isEmailTaken(user.getEmail())) {
            System.out.println("Error: User with email " + user.getEmail() + " already exists.");
            return;
        }
        if (user instanceof Student s) {
            SQLActions.addStudentToDB(s);
        } else if (user instanceof Teacher t) {
            SQLActions.addTeacherToDB(t);
        }
    }

    /**
     * Authenticates a user based on email and password.
     * Compares provided credentials against users in the database and the system SuperUser.
     * @param email the user's email address
     * @param value the user's password
     * @throws IllegalArgumentException if the input format is invalid
     */
    public void login(String email, String value) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(value);

        if (UserSession.isAuthenticated()) {
            System.err.println("User is already logged in");
        }

        ArrayList<User> users = SQLActions.retrieveUsersFromDB();
        users.add(UserSession.getSuperUser());
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(value)) {
                UserSession.login(user);
                makeProtectedPassword(value);
                System.out.println("Logged \n Welcome! User " + user.getName());
                return;
            }
        }

        System.err.println("Incorrect email or password");
    }
    /**
     * Helper method to find a user in the database by their email address.
     * The search priority is Teachers first, then Students.
     * @param email the email address to search for
     * @return the found {@link User} (Teacher or Student), or null if no match is found
     */
    private User findUserByEmail(String email) {
        FieldValidator.validateEmail(email);
        Teacher teacher = SQLActions.getTeacherByEmail(email);
        if (teacher != null) return teacher;
        return SQLActions.getStudentByEmail(email);
    }
    /**
     * Checks if a specific email address is already associated with a registered user.
     * @param email the email to check
     * @return true if the email is taken, false otherwise
     */
    private boolean isEmailTaken(String email) {
        FieldValidator.validateEmail(email);
        return findUserByEmail(email) != null;
    }

    public void removeStudent(int id) {
        validateId(id);
        SQLActions.deleteStudentFromDBWithID(id);
    }

    public void removeTeacher(int id) {
        validateId(id);
        SQLActions.deleteTeacherFromDBWithID(id);
    }
}