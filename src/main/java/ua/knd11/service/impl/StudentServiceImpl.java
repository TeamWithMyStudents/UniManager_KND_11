package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

/**
 * Implementation of the {@link StudentService} interface.
 * Provides high-level business logic for managing student records, including
 * specialized operations like assigning a Head Student within an academic group.
 */
public class StudentServiceImpl implements StudentService {

    /**
     * Assigns the "Head Student" (Starosta) role to a specific student.
     * This operation first demotes any existing head students in the same group
     * before promoting the target student, ensuring only one head exists per group.
     * @param studentId the unique identifier of the student to promote
     */
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

    /**
     * Persists a new student record into the database.
     * @param student the student object to be added
     */
    public void addStudent(Student student) {
        SQLActions.addStudentToDB(student);
    }

    /**
     * Permanently removes a student from the database using their unique ID.
     * @param id the unique identifier of the student to be deleted
     */
    public void deleteStudent(int id) {
        SQLActions.deleteStudentFromDBWithID(id);
    }

    /**
     * Retrieves a complete list of all students currently stored in the database.
     * @return an {@link ArrayList} containing all {@link Student} records
     */
    public ArrayList<Student> getAllStudents() {
        return SQLActions.retrieveStudentsFromDB();
    }
}