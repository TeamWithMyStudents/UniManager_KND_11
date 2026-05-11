package ua.knd11.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Lesson} model class.
 * Tests lesson creation with day of week, time, subject, and teacher validation.
 * Covers null checks, alphabetic validation, and getter/setter functionality.
 *
 * @see Lesson
 */
class LessonTest {

    private Lesson lesson;

    /**
     * Sets up a valid Lesson instance before each test.
     */
    @BeforeEach
    void setUp() {
        lesson = new Lesson(DayOfWeek.MONDAY, LocalTime.of(9, 0), "Mathematics", "Smith");
    }

    /**
     * Tests that a lesson is created correctly with valid data.
     */
    @Test
    void constructor_WithValidData_ShouldCreateLesson() {
        assertNotNull(lesson);
        assertEquals(DayOfWeek.MONDAY, lesson.getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), lesson.getTime());
        assertEquals("Mathematics", lesson.getSubject());
        assertEquals("Smith", lesson.getTeacherSurname());
    }

    /**
     * Tests that null day of week is rejected during construction.
     */
    @Test
    void constructor_WithNullDayOfWeek_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lesson(null, LocalTime.of(9, 0), "Mathematics", "Smith"));
    }

    /**
     * Tests that null time is rejected during construction.
     */
    @Test
    void constructor_WithNullTime_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lesson(DayOfWeek.MONDAY, null, "Mathematics", "Smith"));
    }

    /**
     * Tests that invalid subject names (with digits) are rejected.
     */
    @Test
    void constructor_WithInvalidSubject_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lesson(DayOfWeek.MONDAY, LocalTime.of(9, 0), "Math123", "Smith"));
    }

    /**
     * Tests that invalid teacher surnames (with digits) are rejected.
     */
    @Test
    void constructor_WithInvalidTeacherSurname_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lesson(DayOfWeek.MONDAY, LocalTime.of(9, 0), "Mathematics", "Smith123"));
    }

    /**
     * Tests that lessons can be created for various weekdays.
     *
     * @param dayName the name of the day of week to test
     */
    @ParameterizedTest
    @CsvSource({
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY"
    })
    void constructor_WithVariousDays_ShouldCreateLesson(String dayName) {
        DayOfWeek day = DayOfWeek.valueOf(dayName);
        Lesson dayLesson = new Lesson(day, LocalTime.of(10, 0), "Physics", "Johnson");
        assertEquals(day, dayLesson.getDayOfWeek());
    }

    /**
     * Tests that day of week can be updated to a valid value.
     */
    @Test
    void setDayOfWeek_WithValidDay_ShouldUpdate() {
        lesson.setDayOfWeek(DayOfWeek.TUESDAY);
        assertEquals(DayOfWeek.TUESDAY, lesson.getDayOfWeek());
    }

    /**
     * Tests that null day of week is rejected when updating.
     */
    @Test
    void setDayOfWeek_WithNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> lesson.setDayOfWeek(null));
    }

    /**
     * Tests that time can be updated to a valid value.
     */
    @Test
    void setTime_WithValidTime_ShouldUpdate() {
        LocalTime newTime = LocalTime.of(14, 30);
        lesson.setTime(newTime);
        assertEquals(newTime, lesson.getTime());
    }

    /**
     * Tests that null time is rejected when updating.
     */
    @Test
    void setTime_WithNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> lesson.setTime(null));
    }

    /**
     * Tests that subject can be updated to a valid value.
     */
    @Test
    void setSubject_WithValidSubject_ShouldUpdate() {
        lesson.setSubject("Physics");
        assertEquals("Physics", lesson.getSubject());
    }

    /**
     * Tests that invalid subject values are rejected when updating.
     */
    @Test
    void setSubject_WithInvalidSubject_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> lesson.setSubject("Physics123"));
    }

    /**
     * Tests that teacher surname can be updated to a valid value.
     */
    @Test
    void setTeacherSurname_WithValidSurname_ShouldUpdate() {
        lesson.setTeacherSurname("Johnson");
        assertEquals("Johnson", lesson.getTeacherSurname());
    }

    /**
     * Tests that invalid teacher surnames are rejected when updating.
     */
    @Test
    void setTeacherSurname_WithInvalidSurname_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> lesson.setTeacherSurname("Johnson123"));
    }

    /**
     * Tests that toString contains all relevant lesson information.
     */
    @Test
    void toString_ShouldContainAllRelevantInfo() {
        String result = lesson.toString();

        assertTrue(result.contains("dayOfWeek=MONDAY"));
        assertTrue(result.contains("time=09:00"));
        assertTrue(result.contains("subject='Mathematics'"));
        assertTrue(result.contains("teacherSurname='Smith'"));
    }

    /**
     * Tests that all getter methods return the correct values.
     */
    @Test
    void getters_ShouldReturnCorrectValues() {
        assertEquals(DayOfWeek.MONDAY, lesson.getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), lesson.getTime());
        assertEquals("Mathematics", lesson.getSubject());
        assertEquals("Smith", lesson.getTeacherSurname());
    }
}
