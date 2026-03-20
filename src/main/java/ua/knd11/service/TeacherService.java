package ua.knd11.service;

public interface TeacherService extends UserService {
    void calculateTotalSalary();
    boolean filterByDegree(String degree);
}
