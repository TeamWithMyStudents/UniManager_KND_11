package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link JournalService}.
 * Manages an in-memory repository of student grades and provides functionality
 * to assign grades, retrieve them safely, and generate academic reports.
 */
public class JournalServiceImpl implements JournalService {

    /**
     * Internal in-memory list storing all assigned grades.
     */
    private final List<Grade> grades = new ArrayList<>();

    /**
     * Assigns a new grade to a student for a specific subject and stores it in the repository.
     *
     * @param studentId the unique identifier of the student
     * @param subject   the name of the subject
     * @param score     the numeric score achieved by the student
     */
    @Override
    public void assignGrade(int studentId, String subject, int score) {
        try {
            Grade newGrade = new Grade(studentId, subject, score);
            grades.add(newGrade);
            System.out.println("[LOG] Successfully assigned grade: " + score + " for subject '" + subject + "' to Student ID: " + studentId);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Failed to assign grade: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all grades belonging to a specific student.
     * Returns a defensive copy to prevent external modification of the internal repository.
     *
     * @param studentId the unique identifier of the student
     * @return a list of {@link Grade} objects for the specified student
     */
    @Override
    public List<Grade> getGradesForStudent(int studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)
                // Creating defensive copies to protect the internal state
                .map(original -> new Grade(original.getStudentId(), original.getSubject(), original.getScore()))
                .toList();
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

        StringBuilder report = new StringBuilder();
        report.append("\n==========================================\n");
        report.append("       STUDENT RECORD BOOK (ID: ").append(studentId).append(")\n");
        report.append("==========================================\n");

        int totalScore = 0;
        for (Grade grade : studentGrades) {
            report.append(String.format(" Subject: %-15s | Score: %3d\n",
                    grade.getSubject(), grade.getScore()));
            totalScore += grade.getScore();
        }

        double average = (double) totalScore / studentGrades.size();
        report.append("------------------------------------------\n");
        report.append(String.format(" Average Academic Score: %.2f\n", average));
        report.append("==========================================\n");

        return report.toString();
    }
}