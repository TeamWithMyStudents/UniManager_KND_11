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
     * Gets student id.
     *
     * @return the student id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets student id.
     *
     * @param studentId the student id
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setStudentId(int studentId) throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        this.studentId = studentId;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     * @throws IllegalArgumentException if the subject is not alphabetic writed
     */
    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     * @throws IllegalArgumentException if the score is not in range 0-100
     */
    public void setScore(int score) throws IllegalArgumentException {
        FieldValidator.validateScore(score);
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Grade [Student ID: %d, subject: '%s', score: %d]", studentId, subject, score);
    }
}