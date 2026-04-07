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
     * Creates a Student with the given personal and authentication data after validating inputs.
     *
     * <p>Validates `group` and `lastname`, initializes user fields via {@link User#User(String, String, String, String)},
     * stores `group` in uppercase (Locale.ROOT), assigns `lastname` as provided, and sets the initial role to
     * {@link StudentRole#REGULAR}.
     *
     * @param name     given name
     * @param surname  family name
     * @param lastname family middle/last name; must contain only alphabetic characters
     * @param group    group identifier; validated and stored in uppercase
     * @param email    email address
     * @param password account password
     * @throws IllegalArgumentException if any validation fails
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
     * Retrieves the student's group identifier.
     *
     * @return the student's group identifier in uppercase using Locale.ROOT
     */
    public String getGroup() {
        return group;
    }

    /**
     * Set the student's group code.
     * <p>
     * The provided group is validated and then stored in uppercase using Locale.ROOT.
     *
     * @param group the group code to assign; may be normalized to uppercase
     * @throws IllegalArgumentException if the group value is invalid
     */
    @SuppressWarnings("unused")
    public void setGroup(String group) throws IllegalArgumentException {
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
    }

    /**
     * Retrieves the student's lastname.
     *
     * @return the student's lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the student's lastname after validating it contains only alphabetic characters.
     *
     * @param lastname the student's family name
     * @throws IllegalArgumentException if the lastname is null, empty, or contains non-alphabetic characters
     */
    @SuppressWarnings("unused")
    public void setLastname(String lastname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Lastname", lastname);
        this.lastname = lastname;
    }

    /**
     * Gets the student's role.
     *
     * @return the student's role
     */
    public StudentRole getRole() {
        return role;
    }

    /**
     * Assigns the student's role.
     *
     * @param role the role to assign to the student
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
