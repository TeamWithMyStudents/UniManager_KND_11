package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link JournalService} interface.
 * Provides functionality for recording student grades, retrieving grade histories,
 * and generating academic record reports.
 */
public class JournalServiceImpl implements JournalService {

    /** Internal storage for all grade records in the system */
    private final List<Grade> grades = new ArrayList<>();

    /**
     * Records a new grade for a student in a specific subject.
     * @param studentId the unique identifier of the student
     * @param subject   the name of the academic subject
     * @param score     the numeric score achieved
     */
    @Override
    public void assignGrade(int studentId, String subject, int score) {
        grades.add(new Grade(studentId, subject, score));
    }

    /**
     * Retrieves a list of all grades associated with a specific student.
     * Uses defensive copying to ensure the internal state of the service
     * cannot be modified from outside this class.
     * @param studentId the unique identifier of the student
     * @return a list of {@link Grade} objects for the student
     */
    @Override
    public List<Grade> getGradesForStudent(int studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)
                // Creating defensive copies to protect the internal state
                .map(original -> new Grade(original.getStudentId(), original.getSubject(), original.getScore()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Generates a formatted text-based report (record book) for a specific student.
     * Includes a list of all subjects, corresponding scores, and a calculated average score.
     * @param studentId the unique identifier of the student
     * @return a formatted report string, or a default message if no grades are found
     */
    @Override
    public String generateRecordBook(int studentId) {
        List<Grade> studentGrades = getGradesForStudent(studentId);

        // Handle the case where the student has no recorded grades
        if (studentGrades.isEmpty()) {
            return "Grades are not yet available";
        }

        StringBuilder report = new StringBuilder();
        report.append("--- Student record book (ID: ").append(studentId).append(") ---\n");

        int sum = 0;

        for (Grade grade : studentGrades) {
            report.append("subject: ").append(grade.getSubject())
                    .append(" | score: ").append(grade.getScore())
                    .append("\n");
            sum += grade.getScore();
        }

        // Calculate the average score
        double average = (double) sum / studentGrades.size();

        report.append("--------------------------------------\n");
        report.append(String.format(java.util.Locale.US, "Average score: %.2f\n", average));

        return report.toString();
    }
}