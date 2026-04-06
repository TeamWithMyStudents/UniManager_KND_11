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
     * <p>Constructs a new Teacher instance.
     * <p>Constructor invokes the parent {@link User} constructor to initialize user-related fields,
     * validates department and degree via FieldValidator.validateAlphabeticString, and salary via
     * FieldValidator.validateSalary, then assigns the fields. No normalization is performed.
     *
     * @param name       name
     * @param surname    surname
     * @param department department
     * @param degree     degree or academic qualification of the teacher
     * @param salary     salary
     * @param email      email
     * @param password   password
     * @throws IllegalArgumentException if any field is invalid
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
     * Gets department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets department.
     *
     * @param department the department
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setDepartment(String department) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Department", department);
        this.department = department;
    }

    /**
     * Gets degree.
     *
     * @return the degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets degree.
     *
     * @param degree the degree
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);
        this.degree = degree;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the salary
     * @throws IllegalArgumentException the illegal argument exception
     */
    @SuppressWarnings("unused")
    public void setSalary(double salary) throws IllegalArgumentException {
        FieldValidator.validateSalary(salary);
        this.salary = salary;
    }

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
