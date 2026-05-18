package ua.knd11.service;

import ua.knd11.model.Teacher;

import java.util.ArrayList;

public interface TeacherService {
    void calculateTotalSalary();

    void filterByDegree(String degree);

    void addTeacher(Teacher teacher);

    void deleteTeacher(int id);

    ArrayList<Teacher> getAllTeachers();
}
