package ua.knd11.model;

public abstract class User {
    private static int nextId = 1;
    private final int id;
    private String name;
    private String surname;
    private String lastName;

    public User(String name, String surname, String lastName) {
        this.id = nextId++;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
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

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }


    @Override
    public String toString() {
        return "Id: " + getId() + ", Surname: " + getSurname() + ", Name: " + getName()  + ", LastName: " + getLastName();
    }
}