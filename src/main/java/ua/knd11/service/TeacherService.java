package ua.knd11.service;

/**
 * The interface Teacher service.
 */
public interface TeacherService extends UserService {
    /**
     * Calculate and update the total salary for teachers.
     */
    void calculateTotalSalary();

    /**
     * Restrict the service's teacher set to those that match the specified academic degree.
     *
     * @param degree the academic degree to filter by (for example: "PhD", "MSc")
     */
    void filterByDegree(String degree);
}
