package ua.knd11.model;

import java.util.Objects;

public abstract class User {
    private static int nextId = 1;
    private int id = 0;
    private String name;
    private String surname;
    private String email;
    private String password;

    public User(String name, String surname, String email, String password) {
        this.name = normalizer(name, "Name");
        this.surname = normalizer(surname, "Surname");
        this.email = normalizer(email, "Email");
        this.password = normalizer(password, "Password");
    }

    public static String normalizer(String string, String query) {
        Objects.requireNonNull(string, query + " must not be null");
        if (string.isBlank()) throw new IllegalArgumentException(query + " must not be blank");
        return string.trim();
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
        this.email = normalizer(email, "Email");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = normalizer(password, "Password");
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}