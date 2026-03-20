package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.service.UserService;
import ua.knd11.service.impl.StudentServiceImpl;

public class StudentController {
    private final UserService service = new StudentServiceImpl();

    public void create(String input) {

        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");
        if (parts.length != 4) {
            System.out.println("Error: Expected 5 fields (Surname Name Lastname Group).");
            return;
        }

        String surname = parts[0];
        String name = parts[1];
        String lastname = parts[2];
        String group = parts[3];

        Student student = new Student(surname, name, lastname, group);
        service.add(student);
        System.out.println("Student added successfully!");
    }

    public void delete(int id) {
        if (service.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " is not found.");
        }
    }

    public void getAll() {
        service.getAll().forEach(System.out::println);
    }
}
