package ua.knd11.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ua.knd11.model.Grade;
import ua.knd11.util.SQLActions;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link JournalServiceImpl}.
 * <p>
 * This class contains unit tests to verify the core functionalities of the journal service,
 * including assigning grades, retrieving grades, and generating record books.
 * It uses Mockito to mock the static methods of {@link SQLActions} to isolate the tests
 * from actual database interactions.
 */
class JournalServiceImplTest {

    private JournalServiceImpl journalService;
    private MockedStatic<SQLActions> sqlActionsMock;

    /**
     * Sets up the test environment before each test execution.
     * Initializes a new instance of {@link JournalServiceImpl} and creates a static mock for {@link SQLActions}.
     */
    @BeforeEach
    void setUp() {
        journalService = new JournalServiceImpl();
        sqlActionsMock = mockStatic(SQLActions.class);
    }

    /**
     * Cleans up the test environment after each test execution.
     * Closes the static mock of {@link SQLActions} to prevent memory leaks and ensure
     * that it does not interfere with other tests.
     */
    @AfterEach
    void tearDown() {
        sqlActionsMock.close();
    }

    /**
     * Tests the {@code assignGrade} method.
     * <p>
     * Verifies that when a grade is assigned, the underlying database method
     * {@link SQLActions#addGradeToDB(int, String, int)} is called exactly once with the correct parameters.
     */
    @Test
    void assignGrade_ShouldCallDatabase() {
        journalService.assignGrade(1, "Mathematics", 85);

        sqlActionsMock.verify(() -> SQLActions.addGradeToDB(1, "Mathematics", 85), times(1));
    }

    /**
     * Tests the {@code getGradesForStudent} method under normal conditions.
     * <p>
     * Verifies that the method correctly fetches and returns a populated list of {@link Grade}
     * objects retrieved from the mocked database.
     */
    @Test
    void getGradesForStudent_ShouldReturnListFromDB() {
        List<Grade> expectedGrades = List.of(new Grade(1, "Mathematics", 85));
        sqlActionsMock.when(() -> SQLActions.getGradesForStudent(1)).thenReturn(expectedGrades);

        List<Grade> actualGrades = journalService.getGradesForStudent(1);

        assertEquals(expectedGrades, actualGrades);
    }

    /**
     * Tests the {@code getGradesForStudent} method when no records exist for a student.
     * <p>
     * Verifies that if the database returns an empty list, the service method also
     * returns an empty list without throwing exceptions.
     */
    @Test
    void getGradesForStudent_WithNoGrades_ShouldReturnEmptyList() {
        sqlActionsMock.when(() -> SQLActions.getGradesForStudent(999)).thenReturn(Collections.emptyList());

        List<Grade> grades = journalService.getGradesForStudent(999);

        assertTrue(grades.isEmpty());
    }

    /**
     * Tests the {@code generateRecordBook} method for a student with existing grades.
     * <p>
     * Verifies that the generated string correctly includes the student ID, the names of the subjects,
     * and the accurately calculated average score (accounting for different locale decimal separators).
     */
    @Test
    void generateRecordBook_WithGrades_ShouldGenerateReportAndCalculateAverage() {
        sqlActionsMock.when(() -> SQLActions.getGradesForStudent(1)).thenReturn(List.of(
                new Grade(1, "Mathematics", 80),
                new Grade(1, "Physics", 90)
        ));

        String recordBook = journalService.generateRecordBook(1);

        assertTrue(recordBook.contains("STUDENT RECORD BOOK (ID: 1)"));
        assertTrue(recordBook.contains("Mathematics"));
        assertTrue(recordBook.contains("Physics"));
        assertTrue(recordBook.contains("85,00") || recordBook.contains("85.00")); // Учет локали
    }

    /**
     * Tests the {@code generateRecordBook} method for a student with no grades.
     * <p>
     * Verifies that a specific informational message is returned indicating that
     * no academic records were found for the provided student ID.
     */
    @Test
    void generateRecordBook_WithNoGrades_ShouldReturnMessage() {
        sqlActionsMock.when(() -> SQLActions.getGradesForStudent(999)).thenReturn(Collections.emptyList());

        String recordBook = journalService.generateRecordBook(999);

        assertEquals("No academic records found for Student ID: 999", recordBook);
    }
}