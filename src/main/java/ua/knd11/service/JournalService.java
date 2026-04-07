package ua.knd11.service;

import ua.knd11.model.Grade;

import java.util.List;

/**
 * Service interface for managing student academic records and grades.
 * Defines the contract for assigning grades, retrieving student-specific grade lists,
 * and generating formatted academic reports.
 */
public interface JournalService {

    /**
     * Assigns a new grade to a student for a specific subject.
     *
     * @param studentId the unique identifier of the student receiving the grade
     * @param subject   the name of the academic subject
     * @param score     the numeric score achieved by the student
     */
    void assignGrade(int studentId, String subject, int score);

    /**
     * Retrieves a list of all grades assigned to a specific student.
     *
     * @param studentId the unique identifier of the student
     * @return a list of {@link Grade} objects belonging to the specified student
     */
    List<Grade> getGradesForStudent(int studentId);

    /**
     * Generates a formatted record book (transcript) for a specific student.
     * The report typically includes a list of subjects, corresponding scores,
     * and an overall average.
     *
     * @param studentId the unique identifier of the student
     * @return a formatted string representing the student's academic record
     */
    String generateRecordBook(int studentId);
}