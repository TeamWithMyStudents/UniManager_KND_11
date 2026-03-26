package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.impl.TeacherServiceImpl;

import java.util.List;

public class TeacherController {

    private final TeacherServiceImpl service = new TeacherServiceImpl();
    private final AuthService authService = new AuthServiceImpl();

    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");

        if (parts.length != 7) {
            System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
            return;
        }
        String name = parts[0];
        String surname = parts[1];
        String department = parts[2];
        String degree = parts[3];
        double salary;
        String email = parts[5];
        String password = parts[6];

        try {
            salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);
            if (service.add(teacher)) {
                System.out.println("Teacher added successfully!");
            } else {
                System.out.println("Teacher wasn't added.");
            }
            authService.registration(teacher);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAll() {
        List<User> teachers = service.getAll();
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("No teachers found in the repository.");
        } else {
            for (User user : teachers) {
                System.out.println(user);
            }
        }
    }

    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}

