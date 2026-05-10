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
    /**
     * @deprecated Static counter used to generate the next unique identifier.
     * Use setIdFromDB instead for database-backed ID assignment.
     */
    @Deprecated
    private static int nextId = 1;
    /**
     * The user's password, which must be protected/encrypted
     */
    private String password;
    /**
     * The salt used for password protection
     */
    private String salt;
    /**
     * The unique identifier for this specific user; 0 indicates an unassigned ID
     */
    private int id = 0;
    /**
     * The user's first name
     */
    private String name;
    /**
     * The user's last name (surname)
     */
    private String surname;
    /**
     * The user's unique email address
     */
    private String email;

    /**
     * Constructs a new User instance with the specified name, surname, email, password, and salt.
     * For internal use only, used when loading existing data from a database.
     *
     * @param name     the first name of the user
     * @param surname  the last name of the user
     * @param email    the email address of the user
     * @param password the raw password for the user, which may be salted and secured
     * @param salt     the salt value used for hashing the user's password
     */
    public User(String name, String surname, String email, String password, String salt) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Constructs a new User instance with the specified name, surname, email, and password.
     * Validates the provided inputs for proper formatting and security considerations.
     *
     * @param name     the first name of the user
     * @param surname  the last name of the user
     * @param email    the email address of the user
     * @param password the raw password for the user, which will be salted and hashed for storage
     *
     */
    public User(String name, String surname, String email, String password) {
        FieldValidator.validateAlphabeticString("Name", name);
        FieldValidator.validateAlphabeticString("Surname", surname);
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.salt = FieldValidator.makeProtectedSalt();
        this.password = FieldValidator.makeProtectedPasswordWithSalt(password, this.salt);
    }

    /**
     * @throws IllegalStateException if an ID has already been assigned to this user
     * @deprecated Assigns a unique ID to the user using an internal auto-incrementing counter.
     * Use setIdFromDB instead for database-backed ID assignment.
     */
    @Deprecated
    public void assignId() throws IllegalStateException {
        if (this.id != 0) {
            throw new IllegalStateException("Id has already been assigned");
        }
        this.id = nextId++;
    }

    /**
     * Manually sets the user ID, typically used when loading existing data from a database.
     *
     * @param id the unique ID to be assigned
     */
    public void setIdFromDB(int id) {
        this.id = id;
    }

    /**
     * Updates the user's first name with alphabetic validation.
     *
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
     *
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
     *
     * @param email the new email address
     * @throws IllegalArgumentException if the email format is invalid
     */
    @SuppressWarnings("unused")
    public void setEmail(String email) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        this.email = email;
    }


    /**
     * Returns a basic string representation of the user, excluding sensitive data like passwords.
     *
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