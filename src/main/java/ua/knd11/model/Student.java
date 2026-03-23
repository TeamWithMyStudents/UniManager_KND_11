package ua.knd11.model;

import java.util.Locale;

public class Student extends User {
    private String group;
    private String lastname;

    public Student(String surname, String name, String lastname , String group) {
        super(name, surname);
        this.group = normalizer(group, "Group").toUpperCase(Locale.ROOT);
        this.lastname = normalizer(lastname, "Lastname");
    }

    public String getGroup() {
        return group;
    }

    @SuppressWarnings("unused")
    public void setGroup(String group) {
        this.group = normalizer(group, "Group").toUpperCase(Locale.ROOT);
    }

    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public void setLastname(String lastname) {
        this.lastname = normalizer(lastname, "Lastname");
    }

    @Override
    public String toString() {
        return super.toString()  +  ", Lastname: " + getLastname() + ", Group: " + getGroup();
    }
}
