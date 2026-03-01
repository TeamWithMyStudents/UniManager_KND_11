package ua.knd11.model;

public class Student extends User {
    private String group;

    public Student(String name, String surname, String group) {
        super(name, surname);
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
        return super.toString() + "Group: " + getGroup()+"\n";
    }
}
