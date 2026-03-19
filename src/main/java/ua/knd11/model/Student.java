package ua.knd11.model;

public class Student extends User {
    private String group;
    private String lastname;

    public Student(String surname, String name, String lastname , String group) {
        super(name, surname);
        this.group = group.toUpperCase();
        this.lastname = lastname;
    }

    public String getGroup() {
        return group;
    }

    @SuppressWarnings("unused")
    public void setGroup(String group) {
        this.group = group;
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
