package ua.knd11.service;

import ua.knd11.model.Lesson;
import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleService {

    void addLesson(String day, String time, String subject, String teacherSurname);

    List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek);
}