package ua.knd11.model;

import static ua.knd11.util.FieldValidators.normalizer;
import static ua.knd11.util.FieldValidators.normalizerSalary;


public class Teacher extends User {
    private String department;
    private String degree;
    private double salary;

    public Teacher(String name, String surname, String department, String degree, double salary, String email, String password) {
        super(name, surname, email, password);
        this.department = normalizer(department, "Department");
        this.degree = normalizer(degree, "Degree");
        this.salary = normalizerSalary(salary);
    }

    public String getDepartment() {
        return department;
    }

    @SuppressWarnings("unused")
    public void setDepartment(String department) {
        this.department = normalizer(department, "Department");
    }

    public String getDegree() {
        return degree;
    }

    @SuppressWarnings("unused")
    public void setDegree(String degree) {
        this.degree = normalizer(degree, "Degree");
    }

    public double getSalary() {
        return salary;
    }

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
