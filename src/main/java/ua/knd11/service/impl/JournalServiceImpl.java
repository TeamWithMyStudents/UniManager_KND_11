package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;
import ua.knd11.util.SQLActions;

import java.util.List;

/**
 * Implementation of the {@link JournalService} interface.
 * Provides functionality for recording student grades, retrieving grade histories,
 * and generating academic record reports using PostgreSQL database.
 */
public class JournalServiceImpl implements JournalService {

    /**
     * Assigns a new grade to a student for a specific subject and stores it in the database.
     *
     * @param studentId the unique identifier of the student
     * @param subject   the name of the academic subject
     * @param score     the numeric score achieved
     */
    @Override
    public void assignGrade(int studentId, String subject, int score) {
        try {
            SQLActions.addGradeToDB(studentId, subject, score);
            System.out.println("Successfully assigned grade: " + score + " for subject '" + subject + "' to Student : " + studentId);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Failed to assign grade: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all grades belonging to a specific student from the database.
     *
     * @param studentId the unique identifier of the student
     * @return a list of {@link Grade} objects for the specified student
     */
    @Override
    public List<Grade> getGradesForStudent(int studentId) {
        return SQLActions.getGradesForStudent(studentId);
    }

    /**
     * Generates a formatted record-book report for a student listing subjects, scores, and the average.
     *
     * @param studentId the student's unique identifier
     * @return the formatted record-book string
     */
    @Override
    public String generateRecordBook(int studentId) {
        List<Grade> studentGrades = getGradesForStudent(studentId);

        if (studentGrades.isEmpty()) {
            return "No academic records found for Student ID: " + studentId;
        }

        int totalScore = studentGrades.stream().mapToInt(Grade::getScore).sum();
        double average = (double) totalScore / studentGrades.size();

        StringBuilder report = new StringBuilder();
        report.append(String.format("""
                ==========================================
                        STUDENT RECORD BOOK (ID: %d)
                ==========================================
                """, studentId));

        for (Grade grade : studentGrades) {
            report.append(String.format(" Subject: %-15s | Score: %3d\n", grade.getSubject(), grade.getScore()));
        }

        report.append(String.format("""
                ------------------------------------------
                 Average Academic Score: %.2f
                ==========================================
                """, average));

        return report.toString();
    }
}