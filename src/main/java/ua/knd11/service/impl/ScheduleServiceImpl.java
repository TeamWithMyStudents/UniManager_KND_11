package ua.knd11.service.impl;

import ua.knd11.model.Lesson;
import ua.knd11.service.ScheduleService;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleServiceImpl implements ScheduleService {

    private final List<Lesson> lessons = new ArrayList<>();

    @Override
    public void addLesson(String day, String time, String subject, String teacherSurname) {
        try {
            DayOfWeek dayOfWeekEnum = DayOfWeek.valueOf(day.toUpperCase());
            LocalTime localTime = LocalTime.parse(time);

            Lesson lesson = new Lesson(dayOfWeekEnum, localTime, subject, teacherSurname);
            lessons.add(lesson);
            System.out.println("Урок добавлено");

        } catch (DateTimeParseException | IllegalArgumentException e) {

            throw new IllegalArgumentException("Недійсний формат часу або дня. Очікуваний день, наприклад, «ПОНЕДІЛОК», та час у 24-годинному форматі (наприклад, «14:30»). Деталі: " + e.getMessage());
        }
    }

    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {

        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
    }
}