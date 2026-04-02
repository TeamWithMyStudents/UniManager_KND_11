package ua.knd11.model;

import ua.knd11.model.enums.StudentRole;

import java.util.Locale;

import static ua.knd11.util.FieldValidators.normalizer;

/**
 * <p>The student model that extends from the {@link User}
 * <p>has unique fields group, lastname, and role
 *
 * @see User
 */
public class Student extends User {
    private String group;
    private String lastname;
    private StudentRole role;

    /**
     * <p>Constructs a new Student instance.
     * <p>Constructor uses normalization and validation methods
     * to ensure that provided data is valid. Also, it invokes the
     * parent {@link User} constructor to initialize user-related fields.
     *
     * @param name     name
     * @param surname  surname
     * @param lastname lastname
     * @param group    group
     * @param email    email
     * @param password password
     * @throws IllegalArgumentException if any argument fails validation
     * @throws NullPointerException     if any required argument is null
     */
    public Student(String name, String surname, String lastname, String group, String email, String password) {
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

    public StudentRole getRole() {
        return role;
    }

    public void setRole(StudentRole role) {
        this.role = role;
    }

    //toString for normal output
    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Lastname: " + getLastname() +
                ", Group: " + getGroup() +
                ", Role: " + getRole() +
                ", Email: " + getEmail();
    }
}
