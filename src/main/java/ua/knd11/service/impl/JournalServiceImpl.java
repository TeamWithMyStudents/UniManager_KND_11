package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link JournalService} interface.
 * This class provides in-memory management of student academic records
 * and handles the assignment and retrieval of grades.
 */
public class JournalServiceImpl implements JournalService {

    /**
     * An in-memory list storing all assigned student grades.
     */
    private final List<Grade> grades = new ArrayList<>();

    /**
     * Assigns a new grade to a student for a specific subject.
     *
     * @param studentId the unique identifier of the student
     * @param subject   the name of the academic subject
     * @param score     the numeric score achieved by the student (usually 0-100)
     */
    @Override
    public void assignGrade(int studentId, String subject, int score) {
        try {
            Grade newGrade = new Grade(studentId, subject, score);
            grades.add(newGrade);
            System.out.println("[SUCCESS] Grade securely recorded for Student ID: " + studentId);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Failed to assign grade. " + e.getMessage());
        }
    }

    /**
     * Retrieves all grades assigned to a specific student.
     *
     * @param studentId the unique identifier of the student
     * @return a {@link List} of {@link Grade} objects belonging to the student
     */
    @Override
    public List<Grade> getGradesForStudent(int studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    /**
     * Generates a formatted academic transcript (Record Book) for a student,
     * including all subjects, scores, and the overall average score.
     *
     * @param studentId the unique identifier of the student
     * @return a formatted {@link String} representing the student's academic record
     */
    @Override
    public String generateRecordBook(int studentId) {
        List<Grade> studentGrades = getGradesForStudent(studentId);

        if (studentGrades.isEmpty()) {
            return "No academic records found for student ID: " + studentId;
        }

        StringBuilder recordBook = new StringBuilder();
        recordBook.append("\n=== Academic Transcript | Student ID: ").append(studentId).append(" ===\n");

        int totalScore = 0;
        for (Grade grade : studentGrades) {
            recordBook.append(String.format("- Subject: %-15s | Score: %d\n",
                    grade.getSubject(), grade.getScore()));
            totalScore += grade.getScore();
        }

        double average = (double) totalScore / studentGrades.size();
        recordBook.append("------------------------------------------------\n");
        recordBook.append(String.format("Overall Average Score: %.2f\n", average));
        recordBook.append("================================================\n");

        return recordBook.toString();
    }
}