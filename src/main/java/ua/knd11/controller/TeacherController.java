package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.service.impl.TeacherServiceImpl;
import ua.knd11.util.FieldValidator;

import java.util.List;

/**
 * Controller class for managing teacher-related operations within the terminal interface.
 * Coordinates data flow between the user input and the teacher service layer.
 */
public class TeacherController {

    /** Service implementation instance for teacher business logic */
    private final TeacherServiceImpl service = new TeacherServiceImpl();

    /**
     * Parses a raw string input from the terminal and attempts to add a new teacher.
     * Expects exactly 7 space-separated fields: Name, Surname, Department, Degree, Salary, Email, and Password.
     * @param value the raw string input containing teacher data
     */
    public void addTeacherFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length == 7) {
            service.addTeacher(createTeacherWithParts(parts));
            return;
        }
        System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
    }

    /**
     * Internal helper to validate input parts and create a Teacher model object.
     * Uses {@link FieldValidator} to ensure data integrity for sensitive fields.
     * @param parts an array of strings representing teacher attributes
     * @return a new {@link Teacher} object populated with validated data
     */
    private Teacher createTeacherWithParts(String[] parts) {
        FieldValidator.validateSalary(Double.parseDouble(parts[4]));
        FieldValidator.validateEmail(parts[5]);
        FieldValidator.validatePassword(parts[6]);
        return new Teacher(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5], parts[6]);
    }

    /**
     * Removes a teacher from the system based on their unique identifier.
     * @param id the unique identifier of the teacher to be deleted
     */
    public void deleteTeacher(int id) {
        service.deleteTeacher(id);
    }

    /**
     * Fetches all teachers from the service layer and prints them to the terminal.
     * Displays an empty state message if no teachers are currently registered.
     */
    public void getAll() {
        List<Teacher> teachers = service.getAllTeachers();
        teachers.stream().filter(teacher -> teacher instanceof Teacher).forEach(System.out::println);
        if (teachers.isEmpty()) { System.out.println("No teachers found"); }
    }

    /**
     * Triggers the calculation of the total salary expenditure for all teachers.
     * Results are handled by the service layer's output.
     */
    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    /**
     * Filters and displays teachers based on their academic degree.
     * @param degree the academic degree string to filter by (e.g., "PhD", "Master")
     */
    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}