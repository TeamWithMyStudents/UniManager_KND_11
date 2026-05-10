package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

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
        double result = getAllTeachers().stream()
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
        for (User user : getAllTeachers()) {
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
     * Registers a new teacher by persisting their record to the database.
     *
     * @param teacher the {@link Teacher} object to be added
     * @throws IllegalArgumentException if the teacher data is invalid
     */
    public void addTeacher(Teacher teacher) throws IllegalArgumentException {
        SQLActions.addTeacherToDB(teacher);
    }

    /**
     * Deletes a teacher from the database using their unique identifier.
     *
     * @param id the unique identifier of the teacher to be removed
     */
    @Override
    public void deleteTeacher(int id) {
        SQLActions.deleteTeacherFromDBWithID(id);
    }

    /**
     * Retrieves a list of all teachers stored in the database.
     *
     * @return an {@link ArrayList} containing all {@link Teacher} records
     */
    public ArrayList<Teacher> getAllTeachers() {
        return SQLActions.retrieveTeachersFromDB();
    }
}