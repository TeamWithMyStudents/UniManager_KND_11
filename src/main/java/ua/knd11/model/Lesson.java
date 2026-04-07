package ua.knd11.model;

import ua.knd11.util.FieldValidator;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a scheduled lesson in the university timetable.
 */
public class Lesson {
    private DayOfWeek dayOfWeek;
    private LocalTime time;
    private String subject;
    private String teacherSurname;

    /**
     * Create a Lesson with validated day, time, subject, and teacher surname.
     *
     * @param dayOfWeek      the scheduled day of the lesson; must not be null
     * @param time           the lesson start time; must not be null
     * @param subject        the subject name; must be an alphabetic, non-empty string
     * @param teacherSurname the teacher's surname; must be an alphabetic, non-empty string
     * @throws IllegalArgumentException if {@code dayOfWeek} or {@code time} is null, or if {@code subject}
     *                                  or {@code teacherSurname} fails alphabetic validation
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
     * Gets day of week.
     *
     * @return the day of week
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the lesson's day of week.
     *
     * @param dayOfWeek the new day of week
     * @throws IllegalArgumentException if {@code dayOfWeek} is null
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek must not be null");
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Get the lesson's scheduled start time.
     *
     * @return the scheduled start time of this lesson
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Set the lesson start time.
     *
     * @param time the lesson start time; must not be null
     * @throws IllegalArgumentException if {@code time} is null
     */
    public void setTime(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time must not be null");
        this.time = time;
    }

    /**
     * Gets the lesson subject.
     *
     * @return the lesson subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the lesson's subject after validating its value.
     *
     * @param subject the subject name; must contain only alphabetic characters
     * @throws IllegalArgumentException if {@code subject} is {@code null} or contains non-alphabetic characters
     */
    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

    /**
     * Gets teacher surname.
     *
     * @return the teacher surname
     */
    public String getTeacherSurname() {
        return teacherSurname;
    }

    /**
     * Sets the teacher's surname after validating it contains only alphabetic characters.
     *
     * @param teacherSurname the teacher's surname; must contain only alphabetic characters
     * @throws IllegalArgumentException if `teacherSurname` is null, empty, or contains non-alphabetic characters
     */
    public void setTeacherSurname(String teacherSurname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Teacher Surname", teacherSurname);
        this.teacherSurname = teacherSurname;
    }

    /**
     * Produce a string representation of the lesson containing its day of week, time, subject, and teacher surname.
     *
     * @return a string in the form Lesson{dayOfWeek=..., time=..., subject='...', teacherSurname='...'}
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
