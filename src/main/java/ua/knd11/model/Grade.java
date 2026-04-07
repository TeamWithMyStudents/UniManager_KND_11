package ua.knd11.model;

import ua.knd11.util.FieldValidator;

/**
 * Represents a student's grade for a specific subject.
 */
public class Grade {
    private int studentId;
    private String subject;
    private int score;

    /**
     * Constructs a new Grade with validated information.
     *
     * @param studentId student's unique ID
     * @param subject   subject name
     * @param score     numerical score (0-100)
     * @throws IllegalArgumentException if any field is invalid
     */
    public Grade(int studentId, String subject, int score)
            throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        FieldValidator.validateAlphabeticString("Subject", subject);
        FieldValidator.validateScore(score);
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

    /**
     * Retrieve the student's identifier.
     *
     * @return the student's ID
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the student's identifier.
     *
     * @param studentId the student's identifier; must be a positive integer
     * @throws IllegalArgumentException if the provided `studentId` is invalid
     */
    public void setStudentId(int studentId) throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        this.studentId = studentId;
    }

    /**
     * Subject name associated with this grade.
     *
     * @return the subject name
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject name for this grade.
     *
     * @param subject the subject name consisting only of alphabetic characters
     * @throws IllegalArgumentException if `subject` is null, empty, or contains non-alphabetic characters
     */
    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    /**
     * Retrieves the student's score for the subject.
     *
     * @return the student's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the grade's score.
     *
     * @param score the score value (0-100)
     * @throws IllegalArgumentException if the score is less than 0 or greater than 100
     */
    public void setScore(int score) throws IllegalArgumentException {
        FieldValidator.validateScore(score);
        this.score = score;
    }

    /**
     * Produces a single-line textual representation of this grade.
     *
     * @return a string formatted as "Grade [Student ID: %d, subject: '%s', score: %d]" containing this grade's studentId, subject, and score
     */
    @Override
    public String toString() {
        return String.format("Grade [Student ID: %d, subject: '%s', score: %d]", studentId, subject, score);
    }
}