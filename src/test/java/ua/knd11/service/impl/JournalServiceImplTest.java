package ua.knd11.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.knd11.model.Grade;
import ua.knd11.service.JournalService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link JournalServiceImpl} class.
 * Tests grade assignment, retrieval, defensive copying, and record book generation.
 *
 * @see JournalServiceImpl
 * @see JournalService
 */
class JournalServiceImplTest {

    private JournalService journalService;

    /**
     * Creates a fresh JournalService instance before each test.
     */
    @BeforeEach
    void setUp() {
        journalService = new JournalServiceImpl();
    }

    /**
     * Tests that a grade can be assigned and retrieved for a student.
     */
    @Test
    void assignGrade_ShouldAddGrade() {
        journalService.assignGrade(1, "Mathematics", 85);

        List<Grade> grades = journalService.getGradesForStudent(1);
        assertEquals(1, grades.size());
        assertEquals("Mathematics", grades.get(0).getSubject());
        assertEquals(85, grades.get(0).getScore());
    }

    /**
     * Tests that multiple grades can be assigned to the same student.
     */
    @Test
    void assignGrade_MultipleGradesForSameStudent_ShouldAddAllGrades() {
        journalService.assignGrade(1, "Mathematics", 85);
        journalService.assignGrade(1, "Physics", 90);
        journalService.assignGrade(1, "Chemistry", 78);

        List<Grade> grades = journalService.getGradesForStudent(1);
        assertEquals(3, grades.size());
    }

    /**
     * Tests that grades for different students are properly separated.
     */
    @Test
    void assignGrade_ForDifferentStudents_ShouldSeparateGrades() {
        journalService.assignGrade(1, "Mathematics", 85);
        journalService.assignGrade(2, "Physics", 90);

        List<Grade> gradesForStudent1 = journalService.getGradesForStudent(1);
        List<Grade> gradesForStudent2 = journalService.getGradesForStudent(2);

        assertEquals(1, gradesForStudent1.size());
        assertEquals(1, gradesForStudent2.size());
        assertEquals("Mathematics", gradesForStudent1.get(0).getSubject());
        assertEquals("Physics", gradesForStudent2.get(0).getSubject());
    }

    /**
     * Tests that an empty list is returned for students with no grades.
     */
    @Test
    void getGradesForStudent_WithNoGrades_ShouldReturnEmptyList() {
        List<Grade> grades = journalService.getGradesForStudent(999);
        assertTrue(grades.isEmpty());
    }

    /**
     * Tests that getGradesForStudent returns a defensive copy that doesn't affect internal state.
     */
    @Test
    void getGradesForStudent_ShouldReturnDefensiveCopy() {
        journalService.assignGrade(1, "Mathematics", 85);
        List<Grade> grades = journalService.getGradesForStudent(1);

        grades.clear();

        List<Grade> gradesAfterClear = journalService.getGradesForStudent(1);
        assertEquals(1, gradesAfterClear.size());
    }

    /**
     * Tests that record book generation includes all grades and average calculation.
     */
    @Test
    void generateRecordBook_WithGrades_ShouldGenerateReport() {
        journalService.assignGrade(1, "Mathematics", 85);
        journalService.assignGrade(1, "Physics", 90);
        journalService.assignGrade(1, "Chemistry", 75);

        String recordBook = journalService.generateRecordBook(1);

        assertTrue(recordBook.contains("Student record book (ID: 1)"));
        assertTrue(recordBook.contains("Mathematics"));
        assertTrue(recordBook.contains("Physics"));
        assertTrue(recordBook.contains("Chemistry"));
        assertTrue(recordBook.contains("Average score:"));
    }

    /**
     * Tests that the average score in the record book is calculated correctly.
     */
    @Test
    void generateRecordBook_CalculatesAverageCorrectly() {
        journalService.assignGrade(1, "Mathematics", 80);
        journalService.assignGrade(1, "Physics", 90);

        String recordBook = journalService.generateRecordBook(1);

        assertTrue(recordBook.contains("85.00"));
    }

    /**
     * Tests that a message is returned when generating a record book for students with no grades.
     */
    @Test
    void generateRecordBook_WithNoGrades_ShouldReturnMessage() {
        String recordBook = journalService.generateRecordBook(999);

        assertEquals("Grades are not yet available", recordBook);
    }

    /**
     * Tests that invalid data (student ID, subject name, score) is rejected when assigning grades.
     */
    @Test
    void assignGrade_WithInvalidData_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                journalService.assignGrade(0, "Mathematics", 85));
        assertThrows(IllegalArgumentException.class, () ->
                journalService.assignGrade(1, "Math123", 85));
        assertThrows(IllegalArgumentException.class, () ->
                journalService.assignGrade(1, "Mathematics", 101));
    }
}
