package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

/**
 * An abstract base class representing a generic User in the system.
 * Provides core attributes such as a unique ID, name, email, and password.
 * This class handles basic validation and automatic ID assignment logic.
 */
@Getter
public abstract class User {
    /** Static counter used to generate the next unique identifier */
    private static int nextId = 1;
    /** The unique identifier for this specific user; 0 indicates an unassigned ID */
    private int id = 0;
    /** The user's first name */
    private String name;
    /** The user's last name (surname) */
    private String surname;
    /** The user's unique email address */
    private String email;
    /** The user's password, which must be protected/encrypted */
    private String password;

    /**
     * Constructs a new User and validates their name, surname, and email.
     * Also ensures the password is in a protected format.
     * @param name     the user's first name
     * @param surname  the user's last name
     * @param email    the user's unique email address
     * @param password the user's account password
     * @throws IllegalArgumentException if validation checks fail via {@link FieldValidator}
     */
    public User(String name, String surname, String email, String password) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Name", name);
        FieldValidator.validateAlphabeticString("Surname", surname);
        FieldValidator.validateEmail(email);
        if (!FieldValidator.isPasswordProtected(password)) FieldValidator.makeProtectedPassword(password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Assigns a unique ID to the user using an internal auto-incrementing counter.
     * @throws IllegalStateException if an ID has already been assigned to this user
     */
    public void assignId() throws IllegalStateException {
        if (this.id != 0) {
            throw new IllegalStateException("Id has already been assigned");
        }
        this.id = nextId++;
    }

    /**
     * Manually sets the user ID, typically used when loading existing data from a database.
     * @param id the unique ID to be assigned
     */
    public void setIdFromDB(int id) {
        this.id = id;
    }

    /**
     * Updates the user's first name with alphabetic validation.
     * @param name the new first name
     * @throws IllegalArgumentException if the name is invalid
     */
    @SuppressWarnings("unused")
    public void setName(String name) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Name", name);
        this.name = name;
    }

    /**
     * Updates the user's last name with alphabetic validation.
     * @param surname the new last name
     * @throws IllegalArgumentException if the surname is invalid
     */
    @SuppressWarnings("unused")
    public void setSurname(String surname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Surname", surname);
        this.surname = surname;
    }

    /**
     * Updates the user's email address with format validation.
     * @param email the new email address
     * @throws IllegalArgumentException if the email format is invalid
     */
    @SuppressWarnings("unused")
    public void setEmail(String email) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        this.email = email;
    }

    /**
     * Updates the user's password. The new password must already be in a protected format.
     * @param value the new protected password string
     * @throws IllegalArgumentException if the provided password is not protected
     */
    public void setPassword(String value) throws IllegalArgumentException {
        if (!FieldValidator.isPasswordProtected(value))
            throw new IllegalArgumentException("Cannot set unprotected password");
        this.password = value;
    }

    /**
     * Returns a basic string representation of the user, excluding sensitive data like passwords.
     * @return a string containing ID, name, surname, and email
     */
    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}