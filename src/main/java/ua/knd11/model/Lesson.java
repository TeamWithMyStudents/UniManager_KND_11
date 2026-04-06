package ua.knd11.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static ua.knd11.util.FieldValidators.normalizer;


/**
 * Represents a scheduled lesson in the university timetable.
 * Contains information about the day of week, time, subject, and assigned teacher.
 */
public class Lesson {
    private DayOfWeek dayOfWeek;
    private LocalTime time;
    private String subject;
    private String teacherSurname;

    /**
     * Constructs a new Lesson with the specified details.
     *
     * @param dayOfWeek      the day of the week when the lesson occurs
     * @param time           the start time of the lesson
     * @param subject        the subject name of the lesson
     * @param teacherSurname the surname of the teacher conducting the lesson
     */
    public Lesson(DayOfWeek dayOfWeek, LocalTime time, String subject, String teacherSurname) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.subject = normalizer(subject, "Subject");
        this.teacherSurname = normalizer(teacherSurname, "Teacher Surname");
    }

    /**
     * Gets the day of the week when this lesson occurs.
     *
     * @return the day of week for this lesson
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of the week for this lesson.
     *
     * @param dayOfWeek the new day of week for this lesson
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets the start time of this lesson.
     *
     * @return the lesson start time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the start time for this lesson.
     *
     * @param time the new start time for the lesson
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Gets the subject name of this lesson.
     *
     * @return the lesson subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject name for this lesson.
     *
     * @param subject the new subject name for the lesson
     */
    public void setSubject(String subject) {
        this.subject = normalizer(subject, "Subject");
    }

    /**
     * Gets the surname of the teacher conducting this lesson.
     *
     * @return the teacher's surname
     */
    public String getTeacherSurname() {
        return teacherSurname;
    }

    /**
     * Sets the surname of the teacher for this lesson.
     *
     * @param teacherSurname the new teacher surname for the lesson
     */
    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = normalizer(teacherSurname, "Teacher Surname");
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
