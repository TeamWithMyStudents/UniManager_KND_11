package ua.knd11.model;

public class Student extends User {
    private String group;

    public Student(String surname, String name, String lastname , String group) {
        super(name, surname, lastname);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return super.toString() + " Group: " + getGroup()+"\n";
    }
}
