package ua.knd11.model;

public class Grade {
    private int studentId;
    private String subject;
    private int score;

    public Grade(int studentId, String subject, int score) {
        validateId(studentId);
        validateSubject(subject);
        validateScore(score);
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

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

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID студента має бути додатнім числом.");
        }
    }

    private void validateSubject(String subj) {
        if (subj == null || subj.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва предмету не може бути порожньою.");
        }
    }

    private void validateScore(int sc) {
        if (sc < 0 || sc > 100) {
            throw new IllegalArgumentException("Оцінка повинна бути в межах від 0 до 100.");
        }
    }

    @Override
    public String toString() {
        return String.format("Оцінка [ID студента: %d, Предмет: '%s', Бал: %d]", studentId, subject, score);
    }
}