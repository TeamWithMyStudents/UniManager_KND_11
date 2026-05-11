package ua.knd11.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Grade} model class.
 * Tests grade creation, student ID validation, subject validation, score validation (0-100 range),
 * and all property getters and setters.
 *
 * @see Grade
 */
class GradeTest {

    private Grade grade;

    /**
     * Sets up a valid Grade instance before each test.
     */
    @BeforeEach
    void setUp() {
        grade = new Grade(1, "Mathematics", 85);
    }

    /**
     * Tests that a grade is created correctly with valid data.
     */
    @Test
    void constructor_WithValidData_ShouldCreateGrade() {
        assertNotNull(grade);
        assertEquals(1, grade.getStudentId());
        assertEquals("Mathematics", grade.getSubject());
        assertEquals(85, grade.getScore());
    }

    /**
     * Tests that invalid student IDs (zero and negative) are rejected.
     */
    @Test
    void constructor_WithInvalidStudentId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Grade(0, "Mathematics", 85));
        assertThrows(IllegalArgumentException.class, () -> new Grade(-1, "Mathematics", 85));
    }

    /**
     * Tests that invalid subject names (with digits or empty) are rejected.
     */
    @Test
    void constructor_WithInvalidSubject_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Grade(1, "Math123", 85));
        assertThrows(IllegalArgumentException.class, () -> new Grade(1, "", 85));
    }

    /**
     * Tests that scores outside the valid 0-100 range are rejected.
     *
     * @param invalidScore a score value outside the valid range
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 101, -10, 200})
    void constructor_WithInvalidScore_ShouldThrowException(int invalidScore) {
        assertThrows(IllegalArgumentException.class, () -> new Grade(1, "Mathematics", invalidScore));
    }

    /**
     * Tests that valid scores within the 0-100 range are accepted.
     *
     * @param validScore a score value within the valid range
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 50, 100})
    void constructor_WithValidScore_ShouldCreateGrade(int validScore) {
        Grade validGrade = new Grade(1, "Mathematics", validScore);
        assertEquals(validScore, validGrade.getScore());
    }

    /**
     * Tests that student ID can be updated to a valid positive value.
     */
    @Test
    void setStudentId_WithValidId_ShouldUpdate() {
        grade.setStudentId(42);
        assertEquals(42, grade.getStudentId());
    }

    /**
     * Tests that invalid student IDs are rejected when updating.
     */
    @Test
    void setStudentId_WithInvalidId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> grade.setStudentId(0));
        assertThrows(IllegalArgumentException.class, () -> grade.setStudentId(-5));
    }

    /**
     * Tests that subject can be updated to a valid value.
     */
    @Test
    void setSubject_WithValidSubject_ShouldUpdate() {
        grade.setSubject("Physics");
        assertEquals("Physics", grade.getSubject());
    }

    /**
     * Tests that invalid subject values are rejected when updating.
     */
    @Test
    void setSubject_WithInvalidSubject_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> grade.setSubject("Physics123"));
        assertThrows(IllegalArgumentException.class, () -> grade.setSubject(""));
    }

    /**
     * Tests that score can be updated to a valid value.
     */
    @Test
    void setScore_WithValidScore_ShouldUpdate() {
        grade.setScore(95);
        assertEquals(95, grade.getScore());
    }

    /**
     * Tests that various valid scores can be set.
     *
     * @param validScore a valid score within 0-100 range
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 50, 100})
    void setScore_WithValidScores_ShouldUpdate(int validScore) {
        grade.setScore(validScore);
        assertEquals(validScore, grade.getScore());
    }

    /**
     * Tests that invalid scores are rejected when updating.
     *
     * @param invalidScore a score outside the valid 0-100 range
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 101, -100, 150})
    void setScore_WithInvalidScore_ShouldThrowException(int invalidScore) {
        assertThrows(IllegalArgumentException.class, () -> grade.setScore(invalidScore));
    }

    /**
     * Tests that toString contains all relevant grade information.
     */
    @Test
    void toString_ShouldContainAllRelevantInfo() {
        String result = grade.toString();

        assertTrue(result.contains("Student ID: 1"));
        assertTrue(result.contains("subject: 'Mathematics'"));
        assertTrue(result.contains("score: 85"));
    }

    /**
     * Tests that all getter methods return the correct values for various inputs.
     *
     * @param studentId the student ID to test
     * @param subject   the subject name to test
     * @param score     the score value to test
     */
    @ParameterizedTest
    @CsvSource({
            "1, Mathematics, 85",
            "2, Physics, 100",
            "100, Chemistry, 0"
    })
    void getters_ShouldReturnCorrectValues(int studentId, String subject, int score) {
        Grade testGrade = new Grade(studentId, subject, score);
        assertEquals(studentId, testGrade.getStudentId());
        assertEquals(subject, testGrade.getSubject());
        assertEquals(score, testGrade.getScore());
    }
}
