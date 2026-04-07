package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;

/**
 * Controller for managing operations on students.
 * Processes input data, formats it, and calls the appropriate logic methods.
 */
public class StudentController {
    private final StudentService service = new StudentServiceImpl();

    /**
     * Assigns the student with the given ID as head student.
     *
     * @param id the student's unique identifier
     * @see StudentService#assignHeadStudent(int)
     */
    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }

    /**
     * Creates and adds a new Student parsed from a single-line input string.
     *
     * The input must contain exactly six fields in this order: name, surname, lastname, group, email, password.
     * Fields may be separated by spaces or commas; surrounding whitespace is ignored.
     *
     * @param input single-line student data with six fields in the order: name, surname, lastname, group, email, password
     */
    public void create(String input) {
        //normalize input by replacing commas with spaces and trimming whitespace
        String normalized = input.trim().replace(",", " ");
        //array that splits the normalized string on whitespace into tokens
        String[] parts = normalized.split("\\s+");
        if (parts.length != 6) {
            System.out.println("Error: Expected 6 fields (Surname Name Lastname Group Email@example.com Password).");
            return;
        }
//assigning variables to specific array cells
        String name = parts[0];
        String surname = parts[1];
        String lastname = parts[2];
        String group = parts[3];
        String email = parts[4];
        String password = parts[5];

        try {
            Student student = new Student(name, surname, lastname, group, email, password);
            if (service.add(student)) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Student wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Removes the student with the given identifier from the system.
     *
     * @param id the identifier of the student to remove
     */
    public void delete(int id) {
        if (service.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " is not found.");
        }
    }

    /**
     * Prints all stored Student instances to standard output.
     *
     * Retrieves all users from the service and prints each object that is an instance of {@code Student}. If no students are present, prints a message to {@link System#err}.
     */
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
