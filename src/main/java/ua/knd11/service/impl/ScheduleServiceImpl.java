package ua.knd11.service.impl;

import ua.knd11.model.Lesson;
import ua.knd11.service.ScheduleService;
import ua.knd11.util.SQLActions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Implementation of the {@link ScheduleService}.
 * Manages the university lesson schedule using PostgreSQL database.
 * Supports data validation and bilingual day name parsing (English/Ukrainian).
 */
public class ScheduleServiceImpl implements ScheduleService {

    /**
     * Adds a new lesson to the schedule after validating and parsing the input data.
     *
     * @param day            the day of the week (e.g., "Monday" or "Понеділок")
     * @param time           the starting time in 24-hour format (e.g., "14:30")
     * @param subject        the name of the subject
     * @param teacherSurname the surname of the teacher
     */
    @Override
    public void addLesson(String day, String time, String subject, String teacherSurname) {
        if (day == null || day.trim().isEmpty() || time == null || time.trim().isEmpty()) {
            System.err.println("Day and time cannot be empty.");
            return;
        }

        try {
            Lesson lesson = new Lesson(parseDayOfWeek(day), LocalTime.parse(time.trim()), subject, teacherSurname);
            SQLActions.addLessonToDB(lesson);
            System.out.println("[LOG] Successfully added lesson: " + subject + " on " + parseDayOfWeek(day) + " at " + time);

        } catch (DateTimeParseException | IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid format: " + e.getMessage());
            System.err.println("Hint: Use 'MONDAY' or 'ПОНЕДІЛОК' and time like '14:30'.");
        }
    }

    /**
     * Retrieves all lessons scheduled for a specific day of the week.
     *
     * @param dayOfWeek the targeted {@link DayOfWeek}
     * @return a list of {@link Lesson} objects scheduled for that day
     */
    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return SQLActions.getLessonsByDay(dayOfWeek);
    }

    /**
     * Parses a string representing a day name (in English or Ukrainian) into a {@link DayOfWeek}.
     *
     * @param day the day name string
     * @return the corresponding {@link DayOfWeek}
     * @throws IllegalArgumentException if the day name is not recognized
     */
    public DayOfWeek parseDayOfWeek(String day) {
        String normalized = day.trim().toUpperCase();
        return switch (normalized) {
            case "MONDAY" -> DayOfWeek.MONDAY;
            case "TUESDAY" -> DayOfWeek.TUESDAY;
            case "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "THURSDAY" -> DayOfWeek.THURSDAY;
            case "FRIDAY" -> DayOfWeek.FRIDAY;
            case "SATURDAY" -> DayOfWeek.SATURDAY;
            case "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Unknown day: " + day);
        };
    }
}