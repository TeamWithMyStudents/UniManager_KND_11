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
     * Assigns head student role to a student.
     *
     * @param studentId the student's unique ID
     */
    void assignHeadStudent(int studentId);
}
