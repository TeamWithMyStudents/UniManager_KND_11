package ua.knd11.model;

import lombok.Getter;
import ua.knd11.util.FieldValidator;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class Lesson {
    private DayOfWeek dayOfWeek;
    private LocalTime time;
    private String subject;
    private String teacherSurname;

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

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek must not be null");
        this.dayOfWeek = dayOfWeek;
    }

    public void setTime(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time must not be null");
        this.time = time;
    }

    public void setSubject(String subject) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Subject", subject);
        this.subject = subject;
    }

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
