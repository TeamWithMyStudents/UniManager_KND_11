package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.enums.StudentRole;
import ua.knd11.service.StudentService;
import ua.knd11.util.FieldValidator;
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
        SQLActions.assignHeadStudentTransactional(studentId, student.getGroup());

        System.out.println("Student " + student.getName() + " " + student.getSurname() +
                " is now the Head Student of group " + student.getGroup() + "!");
    }

    /**
     * Validates individual data parts and constructs a new Student object.
     * Performs field-level validation using {@link FieldValidator}.
     *
     * @param parts an array of strings representing the student's attributes
     * @return a new {@link Student} object populated with the validated data
     */
    public Student createStudentWithParts(String[] parts) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("name", parts[0]);
        FieldValidator.validateAlphabeticString("surname", parts[1]);
        FieldValidator.validateGroup(parts[2]);
        FieldValidator.validateEmail(parts[3]);
        FieldValidator.validatePassword(parts[4]);
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    /**
     * Retrieves a complete list of all students currently stored in the database.
     * @return an {@link ArrayList} containing all {@link Student} records
     */
    @Override
    public ArrayList<Student> getAllStudents() {
        return SQLActions.retrieveStudentsFromDB();
    }
}