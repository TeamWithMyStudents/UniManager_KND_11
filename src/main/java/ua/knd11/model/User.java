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
     * Constructs a User instance with the specified name, surname, email, and password.
     *
     * @param name     name
     * @param surname  surname
     * @param email    email
     * @param password password
     * @throws IllegalArgumentException if any of the provided parameters are null, blank, or invalid
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
     * Gets id.
     *
     * @return user's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @throws IllegalArgumentException the illegal argument exception
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
     * Sets surname.
     *
     * @param surname the surname
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setSurname(String surname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Surname", surname);
        this.surname = surname;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setEmail(String email) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setPassword(String password) throws IllegalArgumentException {
        FieldValidator.validatePassword(password);
        this.password = password;
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}