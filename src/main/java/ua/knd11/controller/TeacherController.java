package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.service.impl.TeacherServiceImpl;

public class TeacherController {

    private final TeacherServiceImpl service = new TeacherServiceImpl();

    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");

        if (parts.length != 5) {
            System.out.println("Error: Expected 5 fields (Name Surname Dept Degree Salary).");
            return;
        }
        try {
            String name = parts[0];
            String surname = parts[1];
            String department = parts[2];
            String degree = parts[3];
            double salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(name, surname, department, degree, salary);
            service.add(teacher);
            System.out.println("Teacher added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid salary format. Please enter a number.");
        } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }}
    public void getAll() {service.getAll().forEach(System.out::println);}
    public void calculateTotalSalary() {service.calculateTotalSalary();}
    public void filterByDegree(String degree) {service.filterByDegree(degree);}
    }

