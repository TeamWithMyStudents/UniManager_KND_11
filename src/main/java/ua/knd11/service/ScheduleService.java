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
     * Adds a new lesson to the schedule.
     *
     * @param day            the day of the week for the lesson (e.g., "Monday")
     * @param time           the time the lesson starts (e.g., "08:30")
     * @param subject        the name of the subject or course
     * @param teacherSurname the surname of the teacher conducting the lesson
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