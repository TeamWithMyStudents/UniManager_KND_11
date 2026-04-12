package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

public class StudentServiceImpl implements StudentService {

    public void assignHeadStudent(int studentId) {
        Student newHeadStudent = null;
        for (User u : this.getAllStudents()) {
            if (u.getId() == studentId && u instanceof Student) {
                newHeadStudent = (Student) u;
                break;
            }
        }
        if (newHeadStudent == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }
        String targetGroup = newHeadStudent.getGroup();

        for (User u : this.getAllStudents()) { // TODO: перепишите пж
            if (u instanceof Student st) {
                if (st.getGroup().equals(targetGroup) && st.getRole() == StudentRole.HEAD_STUDENT &&
                        st.getId() != studentId) {
                    st.setRole(StudentRole.REGULAR);
                    System.out.println("Previous Head Student " + st.getName() + " " + st.getSurname() + " " +
                            "\nin group " + st.getGroup() + " stepped down.");
                } else if (st.getGroup().equals(targetGroup) && st.getRole() == StudentRole.HEAD_STUDENT &&
                        st.getId() == studentId) {
                    System.out.println("Student " + st.getName() + " " + st.getSurname() + " " +
                            "\nis already the Head Student of group " + st.getGroup() + ".");
                    return;
                }
            }
        }
        newHeadStudent.setRole(StudentRole.HEAD_STUDENT);
        System.out.println("Student " + newHeadStudent.getName() + " " + newHeadStudent.getSurname() + " " +
                "\nis now the Head Student of group " + targetGroup + "!");
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


