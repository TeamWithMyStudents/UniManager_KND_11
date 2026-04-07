package ua.knd11.service;

/**
 * Service interface for student-specific operations.
 */
public interface StudentService extends UserService {
    /**
     * Finds students by group name.
     *
     * @param groupName the academic group to search for
     */
    void findByGroup(String groupName);

    /**
     * Assigns the head-student role to the student with the given identifier.
     *
     * @param studentId the student's identifier (e.g., database primary key)
     */
    void assignHeadStudent(int studentId);
}
