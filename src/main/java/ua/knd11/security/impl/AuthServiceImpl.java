package ua.knd11.security.impl;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;
import java.util.Scanner;

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
     *
     * @param user the user instance to be registered
     */
    public void registerUser(User user) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString(user.getName(), "name");
        FieldValidator.validateAlphabeticString(user.getSurname(), "surname");
        FieldValidator.validateEmail(user.getEmail());
        FieldValidator.validatePassword(user.getPassword());

        if (isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("\n User with email " + user.getEmail() + " already exists.");
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
        }

        ArrayList<User> users = SQLActions.retrieveUsersFromDB();
        users.add(UserSession.getSuperUser());
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(FieldValidator.makeProtectedPassword(value))) {
                UserSession.login(user);
                System.out.println("Logged \n Welcome! User " + user.getName());
                return;
            }
        }

        System.err.println("Incorrect email or password");
    }

    /**
     * Helper method to find a user in the database by their email address.
     * The search priority is Teachers first, then Students.
     *
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
     *
     * @param email the email to check
     * @return true if the email is taken, false otherwise
     */
    private boolean isEmailTaken(String email) {
        FieldValidator.validateEmail(email);
        return findUserByEmail(email) != null;
    }

    public void removeStudent(int id) {
        try {
            validateId(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        SQLActions.deleteStudentFromDBWithID(id);
    }

    public void removeTeacher(int id) {
        try {
            validateId(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        SQLActions.deleteTeacherFromDBWithID(id);
    }

    public void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter name, surname, group, email, password:");
        String[] parts = new String[5];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = sc.next().trim();
        }

        FieldValidator.validateGroup(parts[2]);
        try {
            registerUser(new Student(parts[0], parts[1], parts[2], parts[3], parts[4]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Student successfully registered!");
    }

    public void addTeacher() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter name, surname, department, degree, salary, email, password:");
        String[] partsT = new String[7];
        for (int i = 0; i < partsT.length; i++) {
            partsT[i] = sc.next().trim();
        }

        FieldValidator.validateAlphabeticString("department", partsT[2]);
        FieldValidator.validateAlphabeticString("degree", partsT[3]);
        FieldValidator.validateSalary(Double.parseDouble(partsT[4]));

        try {
            registerUser(new Teacher(partsT[0], partsT[1], partsT[2], partsT[3], Double.parseDouble(partsT[4]), partsT[5], partsT[6]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Teacher successfully registered!");
    }
}