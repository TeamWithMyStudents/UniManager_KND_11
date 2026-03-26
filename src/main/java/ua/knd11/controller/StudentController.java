package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;

import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;

public class StudentController {
    private final StudentService service = new StudentServiceImpl();
    private final AuthServiceImpl authService = new AuthServiceImpl();

    public void assignHeadStudent(int id) {service.assignHeadStudent(id);}

    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");
        if (parts.length != 6) {
            System.out.println("Error: Expected 6 fields (Surname Name Lastname Group).");
            return;
        }

        String surname = parts[0];
        String name = parts[1];
        String lastname = parts[2];
        String group = parts[3];
        String email = parts[4];
        String password = parts[5];

        Student student = new Student(surname, name, lastname, group,  email, password);
        authService.registration(student);
        if (service.add(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Student wasn't added.");
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
            if (students == null || students.isEmpty()) {
                System.out.println("No students found in the repository.");
            } else {
                for (User user : students) {
                    System.out.println(user);
    }
}}}
