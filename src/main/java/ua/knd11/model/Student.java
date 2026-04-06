package ua.knd11.model;

import ua.knd11.model.enums.StudentRole;
import ua.knd11.util.FieldValidator;

import java.util.Locale;

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
     * <p>Constructor uses validation methods
     * to ensure that provided data is valid. Also, it invokes the
     * parent {@link User} constructor to initialize user-related fields.
     *
     * @param name     name
     * @param surname  surname
     * @param lastname lastname
     * @param group    group
     * @param email    email
     * @param password password
     * @throws IllegalArgumentException if any field is invalid
     */
    public Student(String name, String surname, String lastname, String group, String email, String password)
            throws IllegalArgumentException {
        super(name, surname, email, password);
        FieldValidator.validateGroup(group);
        FieldValidator.validateAlphabeticString("Lastname", lastname);
        this.group = group.toUpperCase(Locale.ROOT);
        this.lastname = lastname;
        this.role = StudentRole.REGULAR;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets group.
     *
     * @param group the group
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setGroup(String group) throws IllegalArgumentException {
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
    }

    /**
     * Gets lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets lastname.
     *
     * @param lastname the lastname
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setLastname(String lastname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Lastname", lastname);
        this.lastname = lastname;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public StudentRole getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(StudentRole role) {
        this.role = role;
    }

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
