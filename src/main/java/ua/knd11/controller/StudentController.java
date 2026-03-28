package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;

public class StudentController {
    private final StudentService service = new StudentServiceImpl();
    private final AuthService authService = new AuthServiceImpl();

    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }

    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");
        if (parts.length != 6) {
            System.out.println("Error: Expected 6 fields (Surname Name Lastname Group Email@example.com Password).");
            return;
        }

        String surname = parts[0];
        String name = parts[1];
        String lastname = parts[2];
        String group = parts[3];
        String email = parts[4];
        String password = parts[5];

        try {
            Student student = new Student(surname, name, lastname, group, email, password);
            if (service.add(student)) {
                authService.registration(student);
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Student wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void delete(int id) {
        if (service.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " is not found.");
        }
    }

    public void getAll() {
        List<User> students = service.getAll();
        boolean found = false;
        for (User student : students) {
            if (student instanceof Student) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.err.println("No students found in the repository.\n");
        }
    }
}
