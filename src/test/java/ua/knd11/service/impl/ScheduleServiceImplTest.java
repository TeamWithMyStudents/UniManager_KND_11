package ua.knd11.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.knd11.model.Lesson;
import ua.knd11.util.SQLActions;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit tests for the {@link ScheduleServiceImpl} class.
 * <p>
 * This test class verifies the core functionality of the schedule service,
 * including parsing days of the week, adding lessons to the schedule,
 * and retrieving lessons by day. It heavily utilizes Mockito to mock
 * static database interactions through {@link SQLActions}.
 */
class ScheduleServiceImplTest {

    private ScheduleServiceImpl scheduleService;

    /**
     * Initializes the testing environment before each test execution.
     * Instantiates a fresh {@link ScheduleServiceImpl} to ensure test isolation.
     */
    @BeforeEach
    void setUp() {
        scheduleService = new ScheduleServiceImpl();
    }

    /**
     * Tests that the {@code parseDayOfWeek} method correctly parses valid
     * string representations of days, ignoring case and leading/trailing whitespace.
     *
     * @param day a valid string representation of Monday (e.g., "MONDAY", "monday", "  Monday  ")
     */
    @ParameterizedTest
    @ValueSource(strings = {"MONDAY", "monday", "  Monday  "})
    void parseDayOfWeek_ShouldReturnCorrectDay_WhenInputIsValid(String day) {
        assertEquals(DayOfWeek.MONDAY, scheduleService.parseDayOfWeek(day));
    }

    /**
     * Tests that the {@code parseDayOfWeek} method throws an {@link IllegalArgumentException}
     * when provided with an invalid or unrecognizable day string.
     */
    @Test
    void parseDayOfWeek_ShouldThrowException_WhenInputIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> scheduleService.parseDayOfWeek("INVALID_DAY"));
        assertTrue(exception.getMessage().contains("Unknown day"));
    }

    /**
     * Tests that the {@code addLesson} method successfully maps valid inputs to a
     * {@link Lesson} object and invokes the database insertion via {@link SQLActions}.
     */
    @Test
    void addLesson_ShouldCallSqlActions_WhenDataIsValid() {
        try (MockedStatic<SQLActions> mockedSql = Mockito.mockStatic(SQLActions.class)) {
            scheduleService.addLesson("MONDAY", "14:30", "Math", "Smith");

            mockedSql.verify(() -> SQLActions.addLessonToDB(any(Lesson.class)), Mockito.times(1));
        }
    }

    /**
     * Tests that the {@code addLesson} method rejects operations and prevents
     * database interaction when mandatory fields (day or time) are null or empty.
     */
    @Test
    void addLesson_ShouldNotCallSqlActions_WhenDayOrTimeIsEmpty() {
        try (MockedStatic<SQLActions> mockedSql = Mockito.mockStatic(SQLActions.class)) {
            scheduleService.addLesson("", "14:30", "Math", "Smith");
            scheduleService.addLesson(null, "14:30", "Math", "Smith");
            scheduleService.addLesson("MONDAY", "", "Math", "Smith");

            mockedSql.verify(() -> SQLActions.addLessonToDB(any(Lesson.class)), Mockito.never());
        }
    }

    /**
     * Tests that the {@code addLesson} method gracefully handles invalid time formats
     * without throwing unhandled exceptions, and ensures no database insertions are attempted.
     */
    @Test
    void addLesson_ShouldHandleException_WhenTimeFormatIsInvalid() {
        try (MockedStatic<SQLActions> mockedSql = Mockito.mockStatic(SQLActions.class)) {
            assertDoesNotThrow(() -> scheduleService.addLesson("MONDAY", "invalid_time", "Math", "Smith"));

            mockedSql.verify(() -> SQLActions.addLessonToDB(any(Lesson.class)), Mockito.never());
        }
    }

    /**
     * Tests that the {@code getLessonsByDay} method correctly fetches and returns
     * a list of lessons for a specific {@link DayOfWeek} by interacting with the database.
     */
    @Test
    void getLessonsByDay_ShouldReturnListOfLessons() {
        Lesson mockLesson = new Lesson(DayOfWeek.MONDAY, LocalTime.of(14, 30), "Math", "Smith");
        List<Lesson> expectedLessons = Collections.singletonList(mockLesson);

        try (MockedStatic<SQLActions> mockedSql = Mockito.mockStatic(SQLActions.class)) {
            mockedSql.when(() -> SQLActions.getLessonsByDay(DayOfWeek.MONDAY)).thenReturn(expectedLessons);

            List<Lesson> actualLessons = scheduleService.getLessonsByDay(DayOfWeek.MONDAY);

            assertEquals(1, actualLessons.size());
            assertEquals("Math", actualLessons.get(0).getSubject());
            mockedSql.verify(() -> SQLActions.getLessonsByDay(DayOfWeek.MONDAY), Mockito.times(1));
        }
    }
}