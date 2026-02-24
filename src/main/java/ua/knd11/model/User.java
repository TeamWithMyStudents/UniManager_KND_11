package ua.knd11.model;

public abstract class User {
    private static int nextId = 1;
    private final int id;
    private String name;
    private String surname;

    public User(String name, String surname) {
        this.id = nextId++;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + ", Name: " + getName() + ", Surname: " + getSurname() + " \n";
    }
}
