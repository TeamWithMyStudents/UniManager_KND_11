package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.ArrayList;
import java.util.List;

public class JournalServiceImpl implements JournalService {

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

                .map(original -> new Grade(original.getStudentId(), original.getSubject(), original.getScore()))
                .toList();
    }

    @Override
    public String generateRecordBook(int studentId) {
        List<Grade> studentGrades = getGradesForStudent(studentId);

        if (studentGrades.isEmpty()) {
            return "Оцінки поки що недоступні";
        }

        StringBuilder report = new StringBuilder();
        report.append("--- Залікова книжка студента (ID: ").append(studentId).append(") ---\n");

        int sum = 0;

        for (Grade grade : studentGrades) {
            report.append("Предмет: ").append(grade.getSubject())
                    .append(" | Бал: ").append(grade.getScore())
                    .append("\n");
            sum += grade.getScore();
        }

        double average = (double) sum / studentGrades.size();

        report.append("--------------------------------------\n");
        report.append(String.format("Середній бал: %.2f\n", average));

        return report.toString();
    }
}