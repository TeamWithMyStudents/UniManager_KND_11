package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Data model representing a scheduled academic lesson.
 * Stores information regarding the day of the week, start time, subject name,
 * and the surname of the assigned teacher.
 */
@Getter
public class Lesson {
    /** The day of the week the lesson takes place */
    private DayOfWeek dayOfWeek;
    /** The specific start time of the lesson */
    private LocalTime time;
    /** The name of the subject being taught */
    private String subject;
    /** The surname of the teacher conducting the lesson */
    private String teacherSurname;

    /**
     * Constructs a new Lesson instance with validation for all fields.
     * @param dayOfWeek      the day of the week (cannot be null)
     * @param time           the start time (cannot be null)
     * @param subject        the name of the subject (must be alphabetic)
     * @param teacherSurname the surname of the teacher (must be alphabetic)
     * @throws IllegalArgumentException if null values are provided or if string validation fails
     */
    public Lesson(DayOfWeek dayOfWeek, LocalTime time, String subject, String teacherSurname)
            throws IllegalArgumentException {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek must not be null");
        if (time == null) throw new IllegalArgumentException("Time must not be null");
        FieldValidator.validateAlphabeticString("Subject", subject);
        FieldValidator.validateAlphabeticString("Teacher Surname", teacherSurname);
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.subject = subject;
        this.teacherSurname = teacherSurname;
    }

    /**
     * Updates the day of the week for this lesson.
     * @param dayOfWeek the new day of the week to set
     * @throws IllegalArgumentException if the provided day is null
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek must not be null");
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Updates the start time for this lesson.
     * @param time the new time to set
     * @throws IllegalArgumentException if the provided time is null
     */
    public void setTime(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time must not be null");
        this.time = time;
    }

    /**
     * Updates the subject name with alphabetic validation.
     * @param subject the new subject name
     * @throws IllegalArgumentException if the name contains non-alphabetic characters
     */
    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    /**
     * Updates the teacher's surname with alphabetic validation.
     * @param teacherSurname the new surname of the teacher
     * @throws IllegalArgumentException if the surname contains non-alphabetic characters
     */
    public void setTeacherSurname(String teacherSurname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Teacher Surname", teacherSurname);
        this.teacherSurname = teacherSurname;
    }

    /**
     * Returns a string representation of the lesson details.
     * @return a string containing the day, time, subject, and teacher surname
     */
    @Override
    public String toString() {
        return "Lesson{" +
                "dayOfWeek=" + dayOfWeek +
                ", time=" + time +
                ", subject='" + subject + '\'' +
                ", teacherSurname='" + teacherSurname + '\'' +
                '}';
    }
}