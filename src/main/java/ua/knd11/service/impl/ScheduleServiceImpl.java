package ua.knd11.service.impl;

import ua.knd11.model.Lesson;
import ua.knd11.service.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ScheduleService}.
 * Manages the lesson schedule using an in-memory list. Handles data validation,
 * parsing of bilingual day names (English/Ukrainian), and time formatting.
 */
public class ScheduleServiceImpl implements ScheduleService {

    /**
     * In-memory repository storing all scheduled lessons.
     */
    private final List<Lesson> lessons = new ArrayList<>();

    /**
     * Adds a new lesson to the schedule after validating and parsing the input data.
     *
     * @param day            the day of the week as a string (e.g., "Monday" or "Понеділок")
     * @param time           the starting time of the lesson in 24-hour format (e.g., "14:30")
     * @param subject        the name of the subject
     * @param teacherSurname the surname of the teacher
     * @throws IllegalArgumentException if the day or time is null, empty, or cannot be parsed
     */
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

    /**
     * Retrieves a list of lessons scheduled for a specific day.
     * Uses Java Streams to filter the internal list.
     *
     * @param dayOfWeek the specific {@link DayOfWeek} enum to filter by
     * @return a list of {@link Lesson} objects scheduled for the requested day
     */
    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
    }

    /**
     * Helper method that parses a string representation of a day of the week
     * into a standard Java {@link DayOfWeek} enum. Supports both English and Ukrainian inputs.
     *
     * @param day the string representation of the day
     * @return the corresponding {@link DayOfWeek} enum value
     * @throws IllegalArgumentException if the provided string does not match any known day
     */
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