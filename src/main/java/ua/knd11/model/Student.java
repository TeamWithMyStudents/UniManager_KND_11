package ua.knd11.model;

import lombok.Getter;
import lombok.Setter;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.util.FieldValidator;

import java.util.Locale;

@Getter
public class Student extends User {
    private String group;
    @Setter
    private StudentRole role;

    public Student(String name, String surname, String group, String email, String password)
            throws IllegalArgumentException {
        super(name, surname, email, password);
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
        this.role = StudentRole.REGULAR;
    }

    @Override
    public void assignId() throws IllegalStateException {
        super.assignId();
    }

    @SuppressWarnings("unused")
    public void setGroup(String group) throws IllegalArgumentException {
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Group: " + getGroup() +
                ", Role: " + getRole() +
                ", Email: " + getEmail();
    }
}
