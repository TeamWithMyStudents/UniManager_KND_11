package ua.knd11.service;

import ua.knd11.model.Student;

import java.util.ArrayList;

public interface StudentService {
    void assignHeadStudent(int studentId);

    Student createStudentWithParts(String[] parts) throws IllegalArgumentException;

    ArrayList<Student> getAllStudents();
}
