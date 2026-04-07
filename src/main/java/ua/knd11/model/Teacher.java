package ua.knd11.model;

import ua.knd11.util.FieldValidator;

/**
 * <p>The teacher model that extends from the {@link User}
 * <p>has unique fields for department, degree, and salary
 *
 * @see User
 */
public class Teacher extends User {
    private String department;
    private String degree;
    private double salary;

    /**
     * Creates a Teacher with the specified personal, contact, and professional details.
     *
     * <p>Validates the department and degree as alphabetic strings and validates the salary value;
     * throws IllegalArgumentException on validation failure.
     *
     * @param name     the teacher's given name
     * @param surname  the teacher's family name
     * @param department the teacher's department or faculty
     * @param degree   the teacher's academic degree or qualification
     * @param salary   the teacher's salary
     * @param email    the teacher's email address
     * @param password the teacher's account password
     * @throws IllegalArgumentException if any provided field fails validation
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
     * Department of the teacher.
     *
     * @return the teacher's department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Set the teacher's department after validating it contains only alphabetic characters.
     *
     * @param department the department name; must contain only alphabetic characters
     * @throws IllegalArgumentException if the department is null, empty, or contains non-alphabetic characters
     */
    @SuppressWarnings("unused")
    public void setDepartment(String department) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Department", department);
        this.department = department;
    }

    /**
     * Retrieves the teacher's academic degree.
     *
     * @return the teacher's academic degree
     */
    public String getDegree() {
        return degree;
    }

    /**
         * Sets the teacher's academic degree or qualification.
         *
         * @param degree the degree or qualification (e.g., "PhD", "Master") to assign
         * @throws IllegalArgumentException if {@code degree} is null, empty, or contains non-alphabetic characters
         */
    @SuppressWarnings("unused")
    public void setDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);
        this.degree = degree;
    }

    /**
     * Get the teacher's salary.
     *
     * @return the current salary of the teacher
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Updates the teacher's salary.
     *
     * @param salary the salary amount
     * @throws IllegalArgumentException if the salary fails validation
     */
    @SuppressWarnings("unused")
    public void setSalary(double salary) throws IllegalArgumentException {
        FieldValidator.validateSalary(salary);
        this.salary = salary;
    }

    /**
     * Produces a single-line string representation of the teacher including id, name, surname, department, degree, and salary.
     *
     * @return a formatted string containing the teacher's id, name, surname, department, degree, and salary
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
