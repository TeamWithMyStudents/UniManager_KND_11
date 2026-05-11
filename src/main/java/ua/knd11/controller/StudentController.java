package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;
import ua.knd11.util.FieldValidator;

import java.util.List;
import java.util.Objects;

/**
 * Controller class responsible for managing student-related operations.
 * It acts as an intermediary between the terminal user interface and the business logic layer.
 */
public class StudentController {

    /**
     * Service layer instance for student data processing
     */
    private final StudentService service;

    /**
     * Constructs a StudentController with the default service implementation.
     */
    public StudentController() {
        this(new StudentServiceImpl());
    }

    /**
     * Constructs a StudentController with a provided service instance.
     * Allows for dependency injection, primarily for testing purposes.
     *
     * @param service the StudentService implementation to use
     */
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Assigns a specific student as the head student based on their unique ID.
     *
     * @param id the unique identifier of the student to be assigned
     */
    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }

    /**
     * Parses a raw string input from the terminal to add a new student.
     * The input is expected to contain exactly 5 space-separated fields:
     * Name, Surname, Group, Email, and Password.
     *
     * @param value the raw string containing student data from the terminal
     */
    public void addStudentFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length != 5) {
            System.out.println("Error: Expected 5 fields (Name Surname Group Email@example.com Password).");
            return;
        }
        try {
            service.addStudent(createStudentWithParts(parts));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Validates individual data parts and constructs a new Student object.
     * Performs field-level validation using {@link FieldValidator}.
     *
     * @param parts an array of strings representing the student's attributes
     * @return a new {@link Student} object populated with the validated data
     */
    private Student createStudentWithParts(String[] parts) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("name", parts[0]);
        FieldValidator.validateAlphabeticString("surname", parts[1]);
        FieldValidator.validateGroup(parts[2]);
        FieldValidator.validateEmail(parts[3]);
        FieldValidator.validatePassword(parts[4]);
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    /**
     * Deletes a student from the system using their unique identifier.
     *
     * @param id the unique ID of the student to be removed
     */
    public void deleteStudent(int id) {
        service.deleteStudent(id);
    }

    /**
     * Retrieves all students from the service and prints them to the console.
     * Displays a "No students found" message if the collection is empty.
     */
    public void getAll() {
        List<Student> students = service.getAllStudents();
        students.stream().filter(Objects::nonNull).forEach(System.out::println);
        if (students.isEmpty()) {
            System.out.println("No students found");
        }
    }
}