package ua.knd11.model;

import java.util.Objects;

public abstract class User {
    private static int nextId = 1;
    private int id = 0;
    private String name;
    private String surname;
    private String email;
    private String password;

    protected User(User other) {
        this.id = other.id;
        this.name = other.name;
        this.surname = other.surname;
        this.email = other.email;
        this.password = other.password;
    }

    public User(String name, String surname, String email, String password) {
        this.name = normalizer(name, "Name");
        this.surname = normalizer(surname, "Surname");
        this.email = credentialsValidation(email, "Email");
        this.password = credentialsValidation(password, "Password");
    }

    public static String normalizer(String string, String type) {
        Objects.requireNonNull(string, type + " must not be null");
        if (string.isBlank()) throw new IllegalArgumentException(type + " must not be blank");

        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!string.matches(regex)) throw new IllegalArgumentException("invalid " + type);

        string = string.trim();
        return string;
    }

    public String credentialsValidation(String credential, String type) {
        if (type.equals("Email")) {
            String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!credential.matches(regex))
                throw new IllegalArgumentException("Invalid email address");
        }

        if (type.equals("Password")) {
            String regex = "^[!@#$%^&*.A-Za-z\\d]{8,}$";
            if (!credential.matches(regex))
                throw new IllegalArgumentException("Invalid password");
        }
        return credential;
    }

    public void assignId() {
        if (this.id != 0) {
            throw new IllegalStateException("Id has already been assigned");
        }
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = normalizer(name, "Name");
    }

    public String getSurname() {
        return surname;
    }

    @SuppressWarnings("unused")
    public void setSurname(String surname) {
        this.surname = normalizer(surname, "Surname");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = credentialsValidation(normalizer(email, "Email"), "Email");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = credentialsValidation(normalizer(password, "Password"), "Password");
    }

    public abstract User copy();

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}