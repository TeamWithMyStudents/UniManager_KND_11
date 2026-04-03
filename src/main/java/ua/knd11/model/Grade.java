package ua.knd11.model;

/**
 * Represents a student's grade in a specific subject.
 * This model ensures data integrity by validating all inputs upon creation
 * and whenever fields are updated.
 */
public class Grade {

    /**
     * The unique identifier of the student.
     */
    private int studentId;

    /**
     * The name of the subject.
     */
    private String subject;

    /**
     * The numeric score achieved by the student (0 to 100).
     */
    private int score;

    /**
     * Constructs a new {@code Grade} instance with the specified details.
     * All inputs are validated before the object is created.
     *
     * @param studentId the unique identifier of the student
     * @param subject   the name of the subject
     * @param score     the score achieved (must be between 0 and 100)
     * @throws IllegalArgumentException if the ID is invalid, the subject is empty, or the score is out of bounds
     */
    public Grade(int studentId, String subject, int score) {
        validateId(studentId);
        validateSubject(subject);
        validateScore(score);
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

    /**
     * Gets the student's unique identifier.
     *
     * @return the student ID
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets a new identifier for the student.
     *
     * @param studentId the new student ID
     * @throws IllegalArgumentException if the ID is less than or equal to zero
     */
    public void setStudentId(int studentId) {
        validateId(studentId);
        this.studentId = studentId;
    }

    /**
     * Gets the name of the subject.
     *
     * @return the subject name
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets a new subject name.
     *
     * @param subject the new subject name
     * @throws IllegalArgumentException if the subject is null or contains only whitespace
     */
    public void setSubject(String subject) {
        validateSubject(subject);
        this.subject = subject;
    }

    /**
     * Gets the student's score.
     *
     * @return the numeric score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets a new score for the student.
     *
     * @param score the new score
     * @throws IllegalArgumentException if the score is not between 0 and 100
     */
    public void setScore(int score) {
        validateScore(score);
        this.score = score;
    }

    /**
     * Validates the student ID.
     *
     * @param id the ID to validate
     * @throws IllegalArgumentException if the ID is not a positive number
     */
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID студента має бути додатнім числом.");
        }
    }

    /**
     * Validates the subject name.
     *
     * @param subj the subject string to validate
     * @throws IllegalArgumentException if the string is null or empty
     */
    private void validateSubject(String subj) {
        if (subj == null || subj.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва предмету не може бути порожньою.");
        }
    }

    /**
     * Validates the score value.
     *
     * @param sc the score to validate
     * @throws IllegalArgumentException if the score is outside the 0-100 range
     */
    private void validateScore(int sc) {
        if (sc < 0 || sc > 100) {
            throw new IllegalArgumentException("Оцінка повинна бути в межах від 0 до 100.");
        }
    }

    /**
     * Returns a string representation of the grade, formatted in Ukrainian.
     *
     * @return a formatted string containing the student ID, subject, and score
     */
    @Override
    public String toString() {
        return String.format("Оцінка [ID студента: %d, Предмет: '%s', Бал: %d]", studentId, subject, score);
    }
}