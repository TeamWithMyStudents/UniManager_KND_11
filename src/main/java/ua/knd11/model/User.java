package ua.knd11.model;

public abstract class User {
    private static int nextId = 1;
    private final int id;
    private String name;
    private String surname;
    private String lastname;

    public User(String name, String surname, String lastname) {
        this.id = nextId++;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }


    @Override
    public String toString() {
        return "Id: " + getId() + ", Surname: " + getSurname() + ", name: " + getname()  + ", Lastname: " + getLastname();
    }
}