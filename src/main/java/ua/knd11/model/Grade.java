package ua.knd11.model;

/**
 * Represents a student's grade in a specific subject.
 * This model ensures data integrity by validating all inputs upon creation
 * and whenever fields are updated.
 */
public class Grade {
    private int studentId;
    private String subject;
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
        this.studentId = validateId(studentId);
        this.subject = validateSubject(subject);
        this.score = validateScore(score);
    }

    /**
     * Gets the student's unique identifier.
     *
     * @return the student id
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
        this.studentId = validateId(studentId);
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
        this.subject = validateSubject(subject);
    }

    /**
     * Gets the student's score.
     *
     * @return the score
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
        this.score = validateScore(score);
    }

    /**
     * Validates the student ID.
     *
     * @param id the ID to validate
     * @throws IllegalArgumentException if the ID is not a positive number
     */
    private int validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The student ID must be a positive number.");
        }
        return id;
    }

    /**
     * Validates the subject name.
     *
     * @param subj the subject string to validate
     * @throws IllegalArgumentException if the string is null or empty
     */
    private String validateSubject(String subj) {
        if (subj == null || subj.trim().isEmpty()) {
            throw new IllegalArgumentException("The name of the item cannot be empty.");
        }
        return subj;
    }

    /**
     * Validates the score value.
     *
     * @param sc the score to validate
     * @throws IllegalArgumentException if the score is outside the 0-100 range
     */
    private int validateScore(int sc) {
        if (sc < 0 || sc > 100) {
            throw new IllegalArgumentException("The score should be between 0 and 100.");
        }
        return sc;
    }

    @Override
    public String toString() {
        return String.format("Grade [Student ID: %d, subject: '%s', score: %d]", studentId, subject, score);
    }
}