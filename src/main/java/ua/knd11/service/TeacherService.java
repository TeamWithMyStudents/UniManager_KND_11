package ua.knd11.service;

public interface TeacherService extends UserService {
    void calculateTotalSalary();
    void filterByDegree(String degree);
}
