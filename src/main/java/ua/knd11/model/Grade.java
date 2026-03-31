package ua.knd11.model;

//Basic Grade model for lessons
public class Grade {
    private int studentId;
    private String subject;
    private int score;

    //Constructor for grades with validate methods
    public Grade(int studentId, String subject, int score) {
        validateId(studentId);
        validateSubject(subject);
        validateScore(score);
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

    //Basic getters and setters
    //in setters uses methods for validate id,subject or numbers for score
    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) {
        validateId(studentId);
        this.studentId = studentId;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) {
        validateSubject(subject);
        this.subject = subject;
    }

    public int getScore() { return score; }

    public void setScore(int score) {
        validateScore(score);
        this.score = score;
    }

    //method which check query id
    //if false throw exception
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID студента має бути додатнім числом.");
        }
    }

    //method which check query subject
    //if false throw exception
    private void validateSubject(String subj) {
        if (subj == null || subj.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва предмету не може бути порожньою.");
        }
    }

    //method which check query subject score
    //if false throw exception
    private void validateScore(int sc) {
        if (sc < 0 || sc > 100) {
            throw new IllegalArgumentException("Оцінка повинна бути в межах від 0 до 100.");
        }
    }

    //toString for normal output
    @Override
    public String toString() {
        return String.format("Оцінка [ID студента: %d, Предмет: '%s', Бал: %d]", studentId, subject, score);
    }
}