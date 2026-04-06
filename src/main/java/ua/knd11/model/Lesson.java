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
     * Constructs a new Lesson with validated information.
     *
     * @param dayOfWeek      day of the week
     * @param time           lesson start time
     * @param subject        subject name
     * @param teacherSurname teacher's surname
     * @throws IllegalArgumentException if subject or teacher name is invalid
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
     * Sets day of week.
     *
     * @param dayOfWeek the day of week
     * @throws IllegalArgumentException if dayOfWeek is null
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek must not be null");
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     * @throws IllegalArgumentException if time is null
     */
    public void setTime(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time must not be null");
        this.time = time;
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
     * @throws IllegalArgumentException the illegal argument exception
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
     * Sets teacher surname.
     *
     * @param teacherSurname the teacher surname
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setTeacherSurname(String teacherSurname) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Teacher Surname", teacherSurname);
        this.teacherSurname = teacherSurname;
    }

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
