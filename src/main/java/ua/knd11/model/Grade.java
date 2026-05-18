package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

/**
 * Data model representing a grade assigned to a student for a specific subject.
 * Includes built-in validation for student IDs, subject names, and score ranges.
 */
@Getter
public class Grade {
    /** The unique identifier of the student associated with this grade */
    private int studentId;
    /** The name of the academic subject */
    private String subject;
    /** The numeric score achieved by the student */
    private int score;

    /**
     * Constructs a new Grade instance with full validation of provided data.
     * @param studentId the unique ID of the student
     * @param subject   the name of the subject (must be alphabetic)
     * @param score     the numeric grade value
     * @throws IllegalArgumentException if any validation check fails via {@link FieldValidator}
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
     * Updates the student ID with validation.
     * @param studentId the new student ID to set
     * @throws IllegalArgumentException if the ID format is invalid
     */
    public void setStudentId(int studentId) throws IllegalArgumentException {
        FieldValidator.validateId(studentId);
        this.studentId = studentId;
    }

    /**
     * Updates the subject name with validation.
     * @param subject the new subject name to set
     * @throws IllegalArgumentException if the name is not purely alphabetic
     */
    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    /**
     * Updates the numeric score with validation.
     * @param score the new score value to set
     * @throws IllegalArgumentException if the score falls outside the allowed range
     */
    public void setScore(int score) throws IllegalArgumentException {
        FieldValidator.validateScore(score);
        this.score = score;
    }

    /**
     * Returns a formatted string representation of the Grade object.
     * @return a string containing the student ID, subject name, and score
     */
    @Override
    public String toString() {
        return String.format("Grade [Student ID: %d, subject: '%s', score: %d]", studentId, subject, score);
    }
}