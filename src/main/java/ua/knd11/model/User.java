package ua.knd11.model;

import java.util.Objects;

import static ua.knd11.service.impl.UserServiceImpl.credentialsValidation;
import static ua.knd11.service.impl.UserServiceImpl.normalizer;

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
        this.email = credentialsValidation(email, "Email");
        this.password = Objects.requireNonNull(credentialsValidation(password, "Password"));
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

    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email = credentialsValidation(email, "Email");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = credentialsValidation(normalizer(password, "Password"), "Password");
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Email: " + getEmail();
    }
}