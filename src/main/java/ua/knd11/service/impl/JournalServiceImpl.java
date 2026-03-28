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
        System.out.println("Оцінку успішно додано до журналу!");
    }

    @Override
    public List<Grade> getGradesForStudent(int studentId) {

        return grades.stream()
                .filter(grade -> grade.getStudentId() == studentId)

                .map(original -> new Grade(original.getStudentId(), original.getSubject(), original.getScore()))
                .toList();
    }
}