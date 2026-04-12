package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

@Getter
public class Teacher extends User {
    private String department;
    private String degree;
    private double salary;

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

    @Override
    public void assignId() throws IllegalStateException {
        super.assignId();
    }

    @SuppressWarnings("unused")
    public void setDepartment(String department) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Department", department);
        this.department = department;
    }

    @SuppressWarnings("unused")
    public void setDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);
        this.degree = degree;
    }

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
