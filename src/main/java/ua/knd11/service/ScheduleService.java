package ua.knd11.service;

import ua.knd11.model.Lesson;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Service interface for managing the lesson schedule.
 * Defines operations for adding new lessons and retrieving the schedule for specific days.
 */
public interface ScheduleService {

    /**
     * Add a lesson to the schedule for a specific day and start time.
     *
     * @param day            day of the week as a full name (e.g., "Monday")
     * @param time           lesson start time in 24-hour "HH:mm" format (e.g., "08:30")
     * @param subject        subject or course name
     * @param teacherSurname teacher's surname
     */
    void addLesson(String day, String time, String subject, String teacherSurname);

    /**
     * Retrieves a list of lessons scheduled for a specific day of the week.
     *
     * @param dayOfWeek the specific {@link DayOfWeek} to get the schedule for
     * @return a list of {@link Lesson} objects scheduled for the given day
     */
    List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek);
}