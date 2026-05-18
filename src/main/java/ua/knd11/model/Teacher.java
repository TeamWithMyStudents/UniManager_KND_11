package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

/**
 * Data model representing a Teacher, extending the base {@link User} class.
 * Stores professional information including academic department, degree, and salary.
 */
@Getter
public class Teacher extends User {
    /** The academic department the teacher belongs to */
    private String department;
    /** The teacher's academic degree (e.g., Professor, PhD, Master) */
    private String degree;
    /** The teacher's monthly or annual salary amount */
    private double salary;

    /**
     * Constructs a new Teacher instance with full field validation.
     * @param name       the first name of the teacher
     * @param surname    the last name of the teacher
     * @param department the department name (must be alphabetic)
     * @param degree     the academic degree (must be alphabetic)
     * @param salary     the salary value (must pass numeric validation)
     * @param email      the unique email address
     * @param password   the account password
     * @throws IllegalArgumentException if validation checks fail via {@link FieldValidator}
     */
    public Teacher(String name, String surname, String department, String degree, double salary, String email, String password)
            throws IllegalArgumentException {
        super(name, surname, email, password);
        FieldValidator.validateAlphabeticString("Department", department);
        FieldValidator.validateAlphabeticString("Degree", degree);
        FieldValidator.validateSalary(salary);
        this.department = department;
        this.degree = degree;
        this.salary = salary;
    }

    /**
     * Constructor for database fill.
     * Bypasses validation and hashing to save stored values.
     *
     * @param name       the first name
     * @param surname    the last name
     * @param department the department name
     * @param degree     the academic degree
     * @param salary     the salary value
     * @param email      the email address
     * @param password   the stored hashed password
     * @param salt       the stored salt
     */
    public Teacher(String name, String surname, String department, String degree, double salary, String email, String password, String salt) {
        super(name, surname, email, password, salt);
        this.department = department;
        this.degree = degree;
        this.salary = salary;
    }

    /**
     * Updates the academic department with alphabetic validation.
     *
     * @param department the new department name
     * @throws IllegalArgumentException if the string contains non-alphabetic characters
     */
    @SuppressWarnings("unused")
    public void setDepartment(String department) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Department", department);
        this.department = department;
    }

    /**
     * Updates the academic degree with alphabetic validation.
     *
     * @param degree the new academic degree
     * @throws IllegalArgumentException if the string contains non-alphabetic characters
     */
    @SuppressWarnings("unused")
    public void setDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);
        this.degree = degree;
    }

    /**
     * Updates the salary amount with numeric validation.
     *
     * @param salary the new salary value
     * @throws IllegalArgumentException if the salary amount is invalid
     */
    @SuppressWarnings("unused")
    public void setSalary(double salary) throws IllegalArgumentException {
        FieldValidator.validateSalary(salary);
        this.salary = salary;
    }

    /**
     * Returns a detailed string representation of the Teacher.
     * Includes inherited fields from {@link User} such as ID and Full Name.
     *
     * @return a formatted string containing teacher professional details
     */
    @Override
    public String toString() {
        return "Id: " + getId() +
                ", Name: " + getName() +
                ", Surname: " + getSurname() +
                ", Department: " + getDepartment() +
                ", Degree: " + getDegree() +
                ", Salary: " + getSalary();
    }
}