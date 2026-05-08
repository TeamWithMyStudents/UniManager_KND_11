package ua.knd11.model;

import lombok.Getter;
import lombok.Setter;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.util.FieldValidator;

import java.util.Locale;

/**
 * Data model representing a Student, extending the base {@link User} class.
 * Includes student-specific attributes such as an academic group and a system role.
 */
@Getter
public class Student extends User {
    /** The academic group the student belongs to (e.g., "KND-11") */
    private String group;
    /** The role of the student in the system, determining specific permissions */
    @Setter
    private StudentRole role;

    /**
     * Constructs a new Student instance and initializes them with a REGULAR role.
     * The group name is automatically normalized to uppercase.
     * @param name     the first name of the student
     * @param surname  the last name of the student
     * @param group    the academic group identifier
     * @param email    the unique email address
     * @param password the account password
     * @throws IllegalArgumentException if field validation fails via {@link FieldValidator}
     */
    public Student(String name, String surname, String group, String email, String password)
            throws IllegalArgumentException {
        super(name, surname, email, password);
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
        this.role = StudentRole.REGULAR;
    }

    /**
     * Assigns a unique identifier to the student using the parent class logic.
     * @throws IllegalStateException if the ID has already been assigned
     */
    @Override
    public void assignId() throws IllegalStateException {
        super.assignId();
    }

    /**
     * Updates the academic group name with validation.
     * Normalizes the input string to uppercase for consistency.
     * @param group the new academic group identifier
     * @throws IllegalArgumentException if the group format is invalid
     */
    @SuppressWarnings("unused")
    public void setGroup(String group) throws IllegalArgumentException {
        FieldValidator.validateGroup(group);
        this.group = group.toUpperCase(Locale.ROOT);
    }

    /**
     * Returns a detailed string representation of the Student.
     * Includes inherited fields from {@link User} such as ID and Email.
     * @return a formatted string of student details
     */
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