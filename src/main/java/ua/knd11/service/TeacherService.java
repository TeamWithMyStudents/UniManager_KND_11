package ua.knd11.service;

import ua.knd11.model.Teacher;

public interface TeacherService {
    void calculateTotalSalary();

    Teacher createTeacherWithParts(String[] parts) throws IllegalArgumentException;

    void filterByDegree(String degree);
}
