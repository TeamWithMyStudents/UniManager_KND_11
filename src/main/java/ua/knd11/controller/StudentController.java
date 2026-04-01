package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;
/**
 * Controller for managing operations on students.
 Processes input data, formats it, and calls the appropriate logic methods.
 */
public class StudentController {
    private final StudentService service = new StudentServiceImpl();

    /**
     * Method assigns a student as a head student by ID.
     * @param id is the student's unique ID.
     * @see StudentService#assignHeadStudent(int)
     */
    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }
    /**
     * Method creates and adds a new student based on the input string.
     * The method normalizes the string, expects 6 fields, removes spaces.
     * @param input string with student data.
     * @throws IllegalArgumentException if the data is not validated in the {@link Student} model.
     */
    public void create(String input) {
        //a variable that replaces spaces with commas
        String normalized = input.trim().replace(",", " ");
        //an array that divides the values entered by the user and stores them in cells
        String[] parts = normalized.split("\\s+");
        if (parts.length != 6) {
            System.out.println("Error: Expected 6 fields (Surname Name Lastname Group Email@example.com Password).");
            return;
        }
//assigning variables to specific array cells
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
     * Method removes a student from the system by their ID.
     * @param id - ID of the student to remove.
     */
    public void delete(int id) {
        if (service.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " is not found.");
        }
    }
    /**
     * Method gets a list of all users and outputs only those who are students to the console.
     * If the list is empty or no students found, outputs {@link System#err}.
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
