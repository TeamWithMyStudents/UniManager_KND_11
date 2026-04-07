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
     * Note: Validation of the input data is handled by the {@link Grade} constructor.
     *
     * @param studentId the unique identifier of the student
     * @param subject   the name of the subject
     * @param score     the numeric score achieved by the student
     */
    @Override
    public void assignGrade(int studentId, String subject, int score) {
        Grade newGrade = new Grade(studentId, subject, score);
        grades.add(newGrade);
    }

    /**
     * Retrieves a list of all grades belonging to a specific student.
     * <p>
     * This method creates and returns a <b>defensive copy</b> of the grades. By mapping
     * the original objects to new {@link Grade} instances, it prevents external
     * modification of the internal repository state.
     * </p>
     *
     * @param studentId the unique identifier of the student whose grades are being retrieved
     * @return a list of copied {@link Grade} objects for the specified student
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
     * Generate a formatted record-book report for a student listing subjects, scores, and the overall average.
     *
     * @param studentId the student's unique identifier
     * @return the formatted record-book string; if the student has no grades, returns "Grades are not yet available"
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
        report.append(String.format("Average score: %.2f\n", average));

        return report.toString();
    }
}