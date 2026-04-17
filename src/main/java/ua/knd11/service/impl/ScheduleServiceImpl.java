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

    private final List<Lesson> lessons = new ArrayList<>(); // TODO: Переписать на ENGLISH, по возможности УКОРОТИТЬ

    @Override
    public void addLesson(String day, String time, String subject, String teacherSurname) {

        if (day == null || day.trim().isEmpty() || time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("The day and time cannot be empty.");
        }
        try {
            Lesson lesson = new Lesson(parseDayOfWeek(day), LocalTime.parse(time.trim()), subject, teacherSurname);
            lessons.add(lesson);
            System.out.println("Lesson added successfully!");

        } catch (DateTimeParseException |
                 IllegalArgumentException e) { // TODO: укоротить либо перенести на несколько строк
            throw new IllegalArgumentException("Invalid time or day format. Expected day (e.g. 'MONDAY') and time in 24h format Details:" + e.getMessage());
        }
    }

    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
    }

    private DayOfWeek parseDayOfWeek(String day) {
        String normalizedDay = day.trim().toUpperCase();
        return switch (normalizedDay) {
            case "MONDAY" -> DayOfWeek.MONDAY;
            case "TUESDAY" -> DayOfWeek.TUESDAY;
            case "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "THURSDAY" -> DayOfWeek.THURSDAY;
            case "FRIDAY" -> DayOfWeek.FRIDAY;
            case "SATURDAY" -> DayOfWeek.SATURDAY;
            case "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Unknown day of the week: " + day);
        };
    }
}