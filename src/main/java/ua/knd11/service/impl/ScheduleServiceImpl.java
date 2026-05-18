package ua.knd11.service.impl;

import ua.knd11.model.Lesson;
import ua.knd11.service.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ScheduleService}.
 * Manages the university lesson schedule using an in-memory repository.
 * Supports data validation and bilingual day name parsing (English/Ukrainian).
 */
public class ScheduleServiceImpl implements ScheduleService {

    /** Internal collection storing all scheduled lessons */
    private final List<Lesson> lessons = new ArrayList<>();

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
            lessons.add(lesson);
            System.out.println("[LOG] Successfully added lesson: " + subject + " on " + dayOfWeekEnum + " at " + time);

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
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .toList();
    }

    /**
     * Parses a string representing a day name (in English or Ukrainian) into a {@link DayOfWeek}.
     *
     * @param day the day name string
     * @return the corresponding {@link DayOfWeek}
     * @throws IllegalArgumentException if the day name is not recognized
     */
    private DayOfWeek parseDayOfWeek(String day) {
        String normalized = day.trim().toUpperCase();
        return switch (normalized) {
            case "ПОНЕДІЛОК", "MONDAY" -> DayOfWeek.MONDAY;
            case "ВІВТОРОК", "TUESDAY" -> DayOfWeek.TUESDAY;
            case "СЕРЕДА", "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "ЧЕТВЕР", "THURSDAY" -> DayOfWeek.THURSDAY;
            case "П'ЯТНИЦЯ", "FRIDAY" -> DayOfWeek.FRIDAY;
            case "СУБОТА", "SATURDAY" -> DayOfWeek.SATURDAY;
            case "НЕДІЛЯ", "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Unknown day: " + day);
        };
    }
}