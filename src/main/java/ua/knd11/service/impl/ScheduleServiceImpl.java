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

        if (day == null || day.trim().isEmpty() || time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("День та час не можуть бути порожніми.");
        }
        try {

            DayOfWeek dayOfWeekEnum = parseDayOfWeek(day);
            LocalTime localTime = LocalTime.parse(time.trim());

            Lesson lesson = new Lesson(dayOfWeekEnum, localTime, subject, teacherSurname);
            lessons.add(lesson);
            System.out.println("Заняття успішно додано!");

        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Неправильний формат часу або дня. Очікується день (наприклад, 'ПОНЕДІЛОК' або 'MONDAY') та час у форматі 24h (наприклад, '14:30'). Деталі: " + e.getMessage());
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
            case "ПОНЕДІЛОК", "MONDAY" -> DayOfWeek.MONDAY;
            case "ВІВТОРОК", "TUESDAY" -> DayOfWeek.TUESDAY;
            case "СЕРЕДА", "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "ЧЕТВЕР", "THURSDAY" -> DayOfWeek.THURSDAY;
            case "П'ЯТНИЦЯ", "ПЯТНИЦЯ", "FRIDAY" -> DayOfWeek.FRIDAY;
            case "СУБОТА", "SATURDAY" -> DayOfWeek.SATURDAY;
            case "НЕДІЛЯ", "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Невідомий день тижня: " + day);
        };
    }
}