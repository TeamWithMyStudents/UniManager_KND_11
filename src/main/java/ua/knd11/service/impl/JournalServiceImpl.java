package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.ArrayList;
import java.util.List;

public class JournalServiceImpl implements JournalService { // TODO: по возможности УКОРОТИТЬ

    private final List<Grade> grades = new ArrayList<>();

    @Override
    public void assignGrade(int studentId, String subject, int score) {
        Grade newGrade = new Grade(studentId, subject, score);
        grades.add(newGrade);
    }

    @Override
    public List<Grade> getGradesForStudent(int studentId) {
        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)
                // Creating defensive copies to protect the internal state
                .map(original -> new Grade(original.getStudentId(), original.getSubject(), original.getScore()))
                .toList();
    }

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