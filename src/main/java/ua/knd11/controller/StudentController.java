package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;

/**
 * Controller for managing {@link Student} entities.
 * It is responsible for handling input data, interacting with the business logic
 * (via {@link StudentService}), and outputting the results of operations to the console.
 */

public class StudentController {

    /**
     * The service used for managing student data.
     */

    private final StudentService service = new StudentServiceImpl();

    /**
     * Assigns the student with the specified identifier as the head student of the group.
     *
     * @param id the unique identifier of the student to be assigned as head
     */
    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }

    /**
     * Creates a new student based on the provided input string.
     * <p>
     * The input string is expected to contain exactly 6 parameters, separated by spaces or commas:
     * Surname, Name, Lastname, Group, Email, Password.
     * </p>
     *
     * @param input the string containing the data for the new student
     */

    public void create(String input) {
        // Normalize the input: replace commas with spaces and split by whitespace
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
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Student wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a student by their unique identifier.
     * Prints a success message if the deletion is successful, or an error message if the student is not found.
     *
     * @param id the unique identifier of the student to be deleted
     */

    public void delete(int id) {
        if (service.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " is not found.");
        }
    }

    /**
     * Retrieves and prints a list of all students to the console.
     * If the repository is empty or contains no students, it outputs a standard error message.
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
