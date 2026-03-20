package ua.knd11.model;

import java.util.Objects;

public class Student extends User {
    private String group;
    private String lastname;

    public Student(String surname, String name, String lastname , String group) {
        super(name, surname);
        this.group = normalizeGroup(group);
        this.lastname = lastname;
    }

    public String getGroup() {
        return group;
    }

    @SuppressWarnings("unused")
    public void setGroup(String group) {
        this.group = normalizeGroup(group);
    }

    private static String normalizeGroup(String group) {
        return Objects.requireNonNull(group, "group must not be null").toUpperCase();
    }

    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return super.toString()  +  ", Lastname: " + getLastname() + ", Group: " + getGroup();
    }
}
