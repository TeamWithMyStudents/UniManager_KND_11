package ua.knd11.model;

import static ua.knd11.util.FieldValidators.normalizer;
import static ua.knd11.util.FieldValidators.normalizerSalary;

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
     * <p>Constructor uses normalization and validation methods
     * to ensure that provided data is valid. Also, it invokes the
     * parent {@link User} constructor to initialize user-related fields.
     *
     * @param name       name
     * @param surname    surname
     * @param department department
     * @param degree     degree or academic qualification of the teacher
     * @param salary     salary
     * @param email      email
     * @param password   password
     * @throws IllegalArgumentException if any argument fails validation
     * @throws NullPointerException     if any required argument is null
     */
    public Teacher(String name, String surname, String department, String degree, double salary, String email, String password) {
        super(name, surname, email, password);
        this.department = normalizer(department, "Department");
        this.degree = normalizer(degree, "Degree");
        this.salary = normalizerSalary(salary);
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
     */
    @SuppressWarnings("unused")
    public void setDepartment(String department) {
        this.department = normalizer(department, "Department");
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
     */
    @SuppressWarnings("unused")
    public void setDegree(String degree) {
        this.degree = normalizer(degree, "Degree");
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
     */
    @SuppressWarnings("unused")
    public void setSalary(double salary) {
        this.salary = normalizerSalary(salary);
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
