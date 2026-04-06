package ua.knd11.service;

/**
 * The interface Teacher service.
 */
public interface TeacherService extends UserService {
    /**
     * Calculate total salary.
     */
    void calculateTotalSalary();

    /**
     * Filter by degree.
     *
     * @param degree the degree
     */
    void filterByDegree(String degree);
}
