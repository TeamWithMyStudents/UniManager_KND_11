package ua.knd11.model;


import ua.knd11.util.FieldValidator;

/**
 * <p>The basic user model from which others are built
 * <p>has fields for id, name, surname, email, password
 *
 * @see Student
 * @see Teacher
 */
public abstract class User {
    private static int nextId = 1;
    private int id = 0;
    private String name;
    private String surname;
    private String email;
    private String password;

    /**
     * Create a User with the given name, surname, email, and password.
     *
     * @param name     the user's first name; must pass alphabetic validation
     * @param surname  the user's surname; must pass alphabetic validation
     * @param email    the user's email; must pass email validation
     * @param password the user's password; must pass password validation
     * @throws IllegalArgumentException if any parameter fails its validation
     */
    public User(String name, String surname, String email, String password) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Name", name);
        FieldValidator.validateAlphabeticString("Surname", surname);
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Assigns an ID to the user if it has not been assigned yet.
     *
     * @throws IllegalStateException if the ID has already been assigned
     */
    public void assignId() throws IllegalStateException {
        if (this.id != 0) {
            throw new IllegalStateException("Id has already been assigned");
        }
        this.id = nextId++;
    }

    /**
     * Retrieve the user's unique identifier.
     *
     * @return the user's unique ID, or 0 if an ID has not been assigned
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user's first name.
     *
     * @return the user's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Update the user's given name.
     *
     * @param name the new given name; must contain only alphabetic characters
     * @throws IllegalArgumentException if `name` is null, empty, or contains non-alphabetic characters
     */
    @SuppressWarnings("unused")
    public void setName(String name) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Name", name);
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Update the user's surname after validating it contains only alphabetic characters.
     *
     * @param surname the new surname to set; must be a non-empty alphabetic string
     * @throws IllegalArgumentException if the provided surname fails validation
     */
    @SuppressWarnings("unused")
    public void setSurname(String surname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Surname", surname);
        this.surname = surname;
    }

    /**
     * Retrieve the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Update the user's email to the provided value after validating its format.
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
     * Retrieves the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Update the user's password after validating it.
     *
     * @param password the new password to assign; must meet the user's password requirements
     * @throws IllegalArgumentException if the provided password is invalid
     */
    public void setPassword(String password) throws IllegalArgumentException {
        FieldValidator.validatePassword(password);
        this.password = password;
    }

    /**
     * Provides a string representation containing the user's id, name, surname, and email.
     *
     * <p>Does not include the user's password.</p>
     *
     * @return a string formatted as "Id: {id}, Name: {name}, Surname: {surname}, Email: {email}".
     */
    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}