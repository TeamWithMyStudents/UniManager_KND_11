package ua.knd11.model;

public class Teacher extends User {
    private String department;
    private String degree;
    private double salary;

    public Teacher(String name, String surname, String department, String degree, double salary) {
        super(name, surname);
        this.department = normalizer(department, "Department");
        this.degree = normalizer(degree, "Degree");
        this.salary = normalizerSalary(salary);
    }

    private static double normalizerSalary(double salary) {
        if (salary <= 0) throw new IllegalArgumentException("Salary cant be negative or zero");
        else return salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = normalizer(department, "Department");
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = normalizer(degree, "Degree");
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = normalizerSalary(salary);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " Name: " + getName() + " Surname: " + getSurname() + " Department: " + getDepartment() + " Degree: " + getDegree() + " Salary: " + getSalary();
    }
}
