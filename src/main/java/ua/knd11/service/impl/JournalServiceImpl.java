package ua.knd11.service.impl;

import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JournalServiceImpl implements JournalService {

    private final List<Grade> grades = new ArrayList<>();

    @Override
    public void assignGrade(int studentId, String subject, int score) {

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }

        Grade newGrade = new Grade(studentId, subject, score);

        grades.add(newGrade);
        System.out.println("Оцінку успішно додано до журналу!");
    }

    @Override
    public List<Grade> getGradesForStudent(int studentId) {

        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)
                .collect(Collectors.toList());
    }
}
