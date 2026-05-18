package ua.knd11.service;

import ua.knd11.model.Student;

import java.util.ArrayList;

public interface StudentService {
    void assignHeadStudent(int studentId);

    void addStudent(Student student);

    void deleteStudent(int id);

    ArrayList<Student> getAllStudents();
}
