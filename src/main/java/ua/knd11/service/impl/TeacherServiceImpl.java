package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the {@link TeacherService} interface.
 * Handles the business logic for teacher management, including salary budget calculations,
 * degree-based filtering, and database persistence operations.
 */
public class TeacherServiceImpl implements TeacherService {

    /**
     * Calculates the total sum of salaries for all teachers currently in the system.
     * Uses the Stream API to map and sum individual salaries.
     * Outputs the "Total University Budget" to the console.
     */
    public void calculateTotalSalary() {
        double result = SQLActions.retrieveTeachersFromDB().stream()
                .mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0)
                .sum();
        System.out.println("Total University Budget: " + result);
    }

    /**
     * Filters and prints teachers who hold a specific academic degree.
     * The search is case-insensitive and validates the input string format.
     *
     * @param degree the academic degree or part of the degree string to filter by
     * @throws IllegalArgumentException if the provided degree string fails alphabetic validation
     */
    public void filterByDegree(String degree) throws IllegalArgumentException {
        try {
            FieldValidator.validateAlphabeticString("Degree", degree);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        boolean found = false;
        for (User user : SQLActions.retrieveTeachersFromDB()) {
            if (user instanceof Teacher t) {
                if (t.getDegree().toLowerCase().contains(degree.toLowerCase())) {
                    System.out.println(t);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No teachers found with the specified degree.");
        }
    }

    /**
     * Internal helper to validate input parts and create a Teacher model object.
     * Uses {@link FieldValidator} to ensure data integrity for sensitive fields.
     *
     * @param parts an array of strings representing teacher attributes
     * @return a new {@link Teacher} object populated with validated data
     */
    public Teacher createTeacherWithParts(String[] parts) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("name", parts[0]);
        FieldValidator.validateAlphabeticString("surname", parts[1]);
        FieldValidator.validateAlphabeticString("department", parts[2]);
        FieldValidator.validateAlphabeticString("degree", parts[3]);
        try {
            FieldValidator.validateSalary(Double.parseDouble(parts[4]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Salary must be a valid number");
        }
        FieldValidator.validateEmail(parts[5]);
        FieldValidator.validatePassword(parts[6]);
        return new Teacher(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5], parts[6]);
    }
}