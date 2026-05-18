package ua.knd11.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.knd11.model.Lesson;
import ua.knd11.service.ScheduleService;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ScheduleServiceImpl} class.
 * Tests lesson addition, retrieval by day, input validation, and time parsing.
 *
 * @see ScheduleServiceImpl
 * @see ScheduleService
 */
class ScheduleServiceImplTest {

    private ScheduleService scheduleService;

    /**
     * Creates a fresh ScheduleService instance before each test.
     */
    @BeforeEach
    void setUp() {
        scheduleService = new ScheduleServiceImpl();
    }

    /**
     * Tests that a lesson can be added with valid string data.
     */
    @Test
    void addLesson_WithValidData_ShouldAddLesson() {
        assertDoesNotThrow(() ->
                scheduleService.addLesson("MONDAY", "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that lessons can be added with various valid data combinations.
     *
     * @param day     the day of week
     * @param time    the time string
     * @param subject the subject name
     * @param teacher the teacher surname
     */
    @ParameterizedTest
    @CsvSource({
            "MONDAY, 09:00, Mathematics, Smith",
            "TUESDAY, 14:30, Physics, Johnson",
            "WEDNESDAY, 10:15, Chemistry, Williams"
    })
    void addLesson_WithVariousValidData_ShouldAddLesson(String day, String time, String subject, String teacher) {
        assertDoesNotThrow(() -> scheduleService.addLesson(day, time, subject, teacher));
    }

    /**
     * Tests that null day of week is rejected.
     */
    @Test
    void addLesson_WithNullDay_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson(null, "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that empty day string is rejected.
     */
    @Test
    void addLesson_WithEmptyDay_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("", "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that blank day string is rejected.
     */
    @Test
    void addLesson_WithBlankDay_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("   ", "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that null time is rejected.
     */
    @Test
    void addLesson_WithNullTime_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("MONDAY", null, "Mathematics", "Smith"));
    }

    /**
     * Tests that empty time string is rejected.
     */
    @Test
    void addLesson_WithEmptyTime_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("MONDAY", "", "Mathematics", "Smith"));
    }

    /**
     * Tests that invalid time format is rejected.
     */
    @Test
    void addLesson_WithInvalidTimeFormat_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("MONDAY", "invalid-time", "Mathematics", "Smith"));
    }

    /**
     * Tests that invalid day of week values are rejected.
     */
    @Test
    void addLesson_WithInvalidDay_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("INVALID_DAY", "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that invalid subject names (with digits) are rejected.
     */
    @Test
    void addLesson_WithInvalidSubject_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("MONDAY", "09:00", "Math123", "Smith"));
    }

    /**
     * Tests that invalid teacher surnames (with digits) are rejected.
     */
    @Test
    void addLesson_WithInvalidTeacherSurname_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.addLesson("MONDAY", "09:00", "Mathematics", "Smith123"));
    }

    /**
     * Tests that an empty list is returned when no lessons exist for a day.
     */
    @Test
    void getLessonsByDay_WithNoLessons_ShouldReturnEmptyList() {
        List<Lesson> lessons = scheduleService.getLessonsByDay(DayOfWeek.MONDAY);
        assertTrue(lessons.isEmpty());
    }

    /**
     * Tests that lessons are correctly grouped and returned by day of week.
     */
    @Test
    void getLessonsByDay_WithLessons_ShouldReturnCorrectLessons() {
        scheduleService.addLesson("MONDAY", "09:00", "Mathematics", "Smith");
        scheduleService.addLesson("MONDAY", "10:30", "Physics", "Johnson");
        scheduleService.addLesson("TUESDAY", "09:00", "Chemistry", "Williams");

        List<Lesson> mondayLessons = scheduleService.getLessonsByDay(DayOfWeek.MONDAY);
        List<Lesson> tuesdayLessons = scheduleService.getLessonsByDay(DayOfWeek.TUESDAY);
        List<Lesson> wednesdayLessons = scheduleService.getLessonsByDay(DayOfWeek.WEDNESDAY);

        assertEquals(2, mondayLessons.size());
        assertEquals(1, tuesdayLessons.size());
        assertTrue(wednesdayLessons.isEmpty());
    }

    /**
     * Tests that various day string capitalizations are handled correctly.
     *
     * @param dayVariation different capitalizations of "monday"
     */
    @ParameterizedTest
    @ValueSource(strings = {"monday", "MONDAY", "Monday", "  monday  "})
    void addLesson_WithVariousDayCapitalizations_ShouldWork(String dayVariation) {
        assertDoesNotThrow(() ->
                scheduleService.addLesson(dayVariation, "09:00", "Mathematics", "Smith"));
    }

    /**
     * Tests that retrieved lessons contain the correct data.
     */
    @Test
    void getLessonsByDay_LessonsShouldHaveCorrectData() {
        scheduleService.addLesson("MONDAY", "09:00", "Mathematics", "Smith");

        List<Lesson> lessons = scheduleService.getLessonsByDay(DayOfWeek.MONDAY);

        assertEquals(1, lessons.size());
        Lesson lesson = lessons.get(0);
        assertEquals(DayOfWeek.MONDAY, lesson.getDayOfWeek());
        assertEquals("Mathematics", lesson.getSubject());
        assertEquals("Smith", lesson.getTeacherSurname());
    }

    /**
     * Tests that multiple lessons can be added for the same day.
     */
    @Test
    void addLesson_MultipleLessonsOnSameDay_ShouldAllBeAdded() {
        scheduleService.addLesson("FRIDAY", "08:00", "Biology", "Brown");
        scheduleService.addLesson("FRIDAY", "09:30", "History", "Davis");
        scheduleService.addLesson("FRIDAY", "11:00", "Geography", "Miller");

        List<Lesson> fridayLessons = scheduleService.getLessonsByDay(DayOfWeek.FRIDAY);
        assertEquals(3, fridayLessons.size());
    }

    /**
     * Tests that time is correctly parsed and stored in the lesson.
     */
    @Test
    void addLesson_LessonWithTime_ShouldHaveCorrectTime() {
        scheduleService.addLesson("WEDNESDAY", "14:45", "Art", "Wilson");

        List<Lesson> lessons = scheduleService.getLessonsByDay(DayOfWeek.WEDNESDAY);
        assertEquals(14, lessons.get(0).getTime().getHour());
        assertEquals(45, lessons.get(0).getTime().getMinute());
    }
}
