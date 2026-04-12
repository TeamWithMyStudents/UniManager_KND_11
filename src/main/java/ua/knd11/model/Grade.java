package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

@Getter
public class Grade {
    private int studentId;
    private String subject;
    private int score;

    public Grade(int studentId, String subject, int score)
            throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        FieldValidator.validateAlphabeticString("Subject", subject);
        FieldValidator.validateScore(score);
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

    public void setStudentId(int studentId) throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        this.studentId = studentId;
    }

    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    public void setScore(int score) throws IllegalArgumentException {
        FieldValidator.validateScore(score);
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Grade [Student ID: %d, subject: '%s', score: %d]", studentId, subject, score);
    }
}