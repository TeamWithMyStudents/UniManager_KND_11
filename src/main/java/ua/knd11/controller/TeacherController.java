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
        String name = parts[0];
        String surname = parts[1];
        String department = parts[2];
        String degree = parts[3];
        double salary;
        try {
            salary = Double.parseDouble(parts[4]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Salary must be a number.");
            return;
        }
        Teacher teacher = new Teacher(name, surname, department, degree, salary);
        if (service.add(teacher)) {
            System.out.println("Teacher added successfully!");
        } else {
            System.out.println("Teacher wasn't added.");
        }
    }

    public void getAll() {
        service.getAll().forEach(System.out::println);
    }

    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    public void filterByDegree(String degree) {
        boolean found = service.filterByDegree(degree);
        if (!found) {
            System.out.println("Teachers with degree " + degree + " are not found.");
        }
    }
}

