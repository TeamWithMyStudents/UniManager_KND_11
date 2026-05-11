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
 * Provides in-memory storage and management for the university timetable.
 */
public class ScheduleServiceImpl implements ScheduleService {

    /**
     * An in-memory list storing the entire lesson schedule.
     */
    private final List<Lesson> schedule = new ArrayList<>();

    /**
     * Adds a new lesson to the university schedule.
     *
     * @param day            the day of the week as a string (e.g., "MONDAY")
     * @param time           the time of the lesson in HH:mm format (e.g., "14:30")
     * @param subject        the name of the subject being taught
     * @param teacherSurname the surname of the teacher conducting the lesson
     */
    @Override
    public void addLesson(String day, String time, String subject, String teacherSurname) {
        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.trim().toUpperCase());
            LocalTime localTime = LocalTime.parse(time.trim());

            Lesson newLesson = new Lesson(dayOfWeek, localTime, subject, teacherSurname);
            schedule.add(newLesson);
            System.out.println("[SUCCESS] Lesson '" + subject + "' added to the schedule on " + dayOfWeek + ".");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("[ERROR] Invalid format. Day must be like 'MONDAY', time like '14:30'.");
        }
    }

    /**
     * Retrieves all scheduled lessons for a specific day of the week.
     *
     * @param dayOfWeek the targeted {@link DayOfWeek}
     * @return a {@link List} of {@link Lesson} objects scheduled for that day
     */
    @Override
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return schedule.stream()
                .filter(lesson -> lesson.getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
    }
}