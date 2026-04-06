package ua.knd11.service;

/**
 * Service interface for managing teacher-specific operations.
 * Extends UserService with additional functionality for teacher management.
 */
public interface TeacherService extends UserService {
    /**
     * Calculates and displays the total salary of all teachers.
     * Uses Stream API to process the teacher list and sum their salaries.
     *
     * @throws IllegalArgumentException if no teachers are found in the repository
     * @throws IllegalStateException    if the repository is empty
     */
    void calculateTotalSalary();

    /**
     * Filters and displays teachers by their academic degree.
     * Searches for teachers with the specified degree and displays the results.
     * If no teachers with the given degree are found, displays an appropriate message.
     *
     * @param degree the academic degree to filter by (e.g., "PhD", "Master")
     * @throws IllegalArgumentException if the degree is null or empty
     */
    void filterByDegree(String degree);
}
