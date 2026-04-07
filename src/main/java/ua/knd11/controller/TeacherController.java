package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.impl.TeacherServiceImpl;

import java.util.List;

/**
 * Controller class responsible for handling teacher-related operations.
 * It acts as an intermediary between the user interface (e.g., ConsoleMenu)
 * and the underlying {@link TeacherServiceImpl} business logic layer.
 */
public class TeacherController {

    /**
     * The service used for executing business logic related to teachers.
     */
    private final TeacherServiceImpl service = new TeacherServiceImpl();

    /**
     * Creates and adds a new teacher to the system by parsing a single input string.
     * <p>
     * The input string must contain exactly 7 fields separated by spaces or commas:
     * Name, Surname, Department, Degree, Salary, Email, and Password.
     * <p>
     * Example of a valid input: {@code "John Doe CS PhD 5000.50 john@example.com pass123"}
     *
     * @param input a raw string containing the teacher's details.
     */
    public void create(String input) {
        //normalize commas to spaces and trim whitespace
        String normalized = input.trim().replace(",", " ");
        //array that splits user input into tokens and stores them in elements
        String[] parts = normalized.split("\\s+");

        if (parts.length != 7) {
            System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
            return;
        }

        //assigning variables to specific array indexes
        String name = parts[0];
        String surname = parts[1];
        String department = parts[2];
        String degree = parts[3];
        double salary;
        String email = parts[5];
        String password = parts[6];

        //A new teacher is created, into which the previously created variables are entered.
        //Calls service.add() implemented in TeacherServiceImpl to add the teacher
        try {
            salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);
            if (service.add(teacher)) {
                System.out.println("Teacher added successfully!");
            } else {
                System.out.println("Teacher wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves and prints a list of all teachers currently stored in the repository.
     * <p>
     * This method fetches all users from the underlying service, filters the list to
     * include only instances of {@link Teacher}, and prints their details to the
     * standard output stream. If no teachers are found, an error message is printed
     * to the standard error stream.
     */
    public void getAll() {
        List<User> teachers = service.getAll();
        boolean found = false;
        for (User teacher : teachers) {
            if (teacher instanceof Teacher) {
                System.out.println(teacher);
                found = true;
            }
        }

        if (!found) {
            System.err.println("No teachers found in the repository.\n");
        }
    }

    /**
     * Calculates the total salary of all teachers.
     */
    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    /**
     * Filters teachers by academic degree and displays matching results.
     *
     * @param degree the academic degree to filter by (e.g., "Master", "PhD", "Doctor of Science")
     */
    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}

