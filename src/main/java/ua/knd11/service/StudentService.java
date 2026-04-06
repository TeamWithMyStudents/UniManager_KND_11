package ua.knd11.service;

/**
 * Service interface for managing student-specific operations.
 * Extends UserService with additional functionality for student management.
 */
public interface StudentService extends UserService {
    /**
     * Searches for students by group name and displays the results.
     * Uses partial matching and is case-insensitive.
     *
     * @param groupName the group name to search for (e.g., "KND-11")
     * @throws IllegalArgumentException if the group name is null or empty
     */
    void findByGroup(String groupName);

    /**
     * Assigns a student as the Head Student of their group.
     * <p>
     * The method follows this logic:
     * <ul>
     * <li>Searches for a student by ID. If not found, throws an error.</li>
     * <li>Checks if the current Head Student is in the same group.</li>
     * <li>If another Head Student exists in the group, they are demoted to REGULAR.</li>
     * <li>If the target student is already Head Student, displays a message and returns.</li>
     * <li>Assigns the new Head Student role and displays confirmation.</li>
     * </ul>
     *
     * @param studentId the unique ID of the student to be assigned as Head Student
     * @throws IllegalArgumentException if the student ID is invalid or student not found
     */
    void assignHeadStudent(int studentId);
}
