package ua.knd11.model;

import java.time.DayOfWeek;
import java.time.LocalTime;


public class Lesson {
    private DayOfWeek dayOfWeek;
    private LocalTime time;
    private String subject;
    private String teacherSurname;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
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

    public Lesson(DayOfWeek dayOfWeek, LocalTime time, String subject, String teacherSurname) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.subject = subject;
        this.teacherSurname = teacherSurname;


    }
}
