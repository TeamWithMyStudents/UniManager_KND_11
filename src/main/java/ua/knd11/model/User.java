package ua.knd11.model;


import lombok.Getter;
import ua.knd11.util.FieldValidator;

@Getter
public abstract class User {
    private static int nextId = 1;
    private int id = 0;
    private String name;
    private String surname;
    private String email;
    private String password;

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

    public void assignId() throws IllegalStateException {
        if (this.id != 0) {
            throw new IllegalStateException("Id has already been assigned");
        }
        this.id = nextId++;
    }
    public void setIdFromDB(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setName(String name) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Name", name);
        this.name = name;
    }

    @SuppressWarnings("unused")
    public void setSurname(String surname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Surname", surname);
        this.surname = surname;
    }

    @SuppressWarnings("unused")
    public void setEmail(String email) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        this.email = email;
    }

    public void setPassword(String value) throws IllegalArgumentException {
        if (!FieldValidator.isPasswordProtected(value))
            throw new IllegalArgumentException("Cannot set unprotected password");
        this.password = value;
    }
//там была идея про суперюзера, который может всех добавлять, удалять и тд.
        public boolean isSuperuser() {
        return (this instanceof Teacher) && (this.id == 1);
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}