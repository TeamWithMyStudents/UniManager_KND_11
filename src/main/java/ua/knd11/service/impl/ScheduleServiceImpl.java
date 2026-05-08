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
 * Implementation of the {@link ScheduleService} interface.
 * Manages the academic timetable by allowing the addition of lessons
 * and providing lookup functionality filtered by the day of the week.
 */
public class ScheduleServiceImpl implements ScheduleService {

    /** Internal collection storing all scheduled lessons */
    private final List<Lesson> lessons = new ArrayList<>();

    /**
     * Adds a new lesson to the schedule after validating and parsing the input.
     * Expects a day of the week and a time in 24-hour format (e.g., "14:30").
     * @param day            the day of the week as a string (e.g., "MONDAY")
     * @param time           the start time as a string in 24h format
     * @param subject        the name of the subject
     * @param teacherSurname the surname of the teacher
     * @throws IllegalArgumentException if the day or time formats are invalid or if inputs are empty
     */
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
                 IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid time or day format. Expected day (e.g. 'MONDAY') and time in 24h format Details:" + e.getMessage());
        }
    }

    /**
     * Filters the total schedule to return lessons occurring on a specific day.
     * @param dayOfWeek the {@link DayOfWeek} to filter the schedule by
     * @return a list of {@link Lesson} objects scheduled for the given day
     */
    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
    }

    /**
     * Normalizes a string input and converts it into a {@link DayOfWeek} enum.
     * @param day the string representation of the day
     * @return the corresponding {@link DayOfWeek}
     * @throws IllegalArgumentException if the string does not match any day of the week
     */
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