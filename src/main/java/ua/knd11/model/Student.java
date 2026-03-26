package ua.knd11.model;
import ua.knd11.model.enums.StudentRole;
import java.util.Locale;

public class Student extends User {
    private String group;
    private String lastname;
    private StudentRole role;

    public Student(String surname, String name, String lastname , String group, String email, String password) {
        super(name, surname, email, password);
        this.group = normalizer(group, "Group").toUpperCase(Locale.ROOT);
        this.lastname = normalizer(lastname, "Lastname");
        this.role = StudentRole.REGULAR;
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

    public StudentRole getRole() {return role;}

    public void setRole(StudentRole role) {this.role = role;}

    @Override
    public String toString() {
        return super.toString()  +  ", Lastname: " + getLastname() + ", Group: " + getGroup()  + ", Role: "+ getRole() + ", Email: " + getEmail() + ", Password: " + getPassword();
    }
}
