package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

public class StudentServiceImpl implements StudentService {
    @Override
    public void assignHeadStudent(int studentId) {
        Student student = SQLActions.getStudentById(studentId);

        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }
        if (student.getRole() == StudentRole.HEAD_STUDENT) {
            System.out.println("Student " + student.getName() + " is already the Head of group " + student.getGroup());
            return;
        }
        SQLActions.demoteAllHeadsInGroup(student.getGroup());
        SQLActions.updateStudentRole(studentId, StudentRole.HEAD_STUDENT);

        System.out.println("Student " + student.getName() + " " + student.getSurname() +
                " is now the Head Student of group " + student.getGroup() + "!");
    }

    public void addStudent(Student student) {
        SQLActions.addStudentToDB(student);
    }

    public void deleteStudent(int id) {
        SQLActions.deleteStudentFromDBWithID(id);
    }

    public ArrayList<Student> getAllStudents() {
        return SQLActions.retrieveStudentsFromDB();
    }
}


