package ua.knd11.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knd11.service.impl.JournalServiceImpl;
import ua.knd11.service.impl.ScheduleServiceImpl;
import ua.knd11.util.SQLActions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link TeacherController} class.
 * Tests teacher input parsing, validation, salary handling, and controller method invocation.
 *
 * @see TeacherController
 */
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {


    private TeacherController teacherController;

    /**
     * Sets up the controller before each test.
     */
    @BeforeEach
    void setUp() {
        teacherController = new TeacherController(new JournalServiceImpl(), new ScheduleServiceImpl());
    }

    @AfterAll
    static void tearDown() {
        SQLActions.deleteTeacherFromDBWithEmail("jane.smith@university.edu");
        SQLActions.deleteTeacherFromDBWithEmail("jane.smith2t@university.edu");
    }

    /**
     * Tests that valid teacher input is processed without throwing exceptions.
     */
    @Test
    void addTeacherFromTerminal_WithValidInput_ShouldWork() {
        String validInput = "Jane Smith ComputerScience PhD 50000 jane.smith@university.edu Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(validInput));
    }

    /**
     * Tests that input with too few fields is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithTooFewFields_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that input with too many fields is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithTooManyFields_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 jane@university.edu Password123! extra";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that invalid name format is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidName_ShouldPrintError() {
        String invalidInput = "Jane123 Smith ComputerScience PhD 50000 jane@university.edu Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that invalid salary format is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidSalaryFormat_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD invalid jane@university.edu Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that negative salary values are handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithNegativeSalary_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD -1000 jane@university.edu Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that zero salary values are handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithZeroSalary_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 0 jane@university.edu Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that invalid email format is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidEmail_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 invalid-email Password123!";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that short password is handled gracefully.
     */
    @Test
    void addTeacherFromTerminal_WithShortPassword_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 jane@university.edu short";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests that delete teacher operation executes without throwing exceptions.
     */
    @Test
    void deleteTeacher_ShouldWork() {
        assertDoesNotThrow(() -> SQLActions.deleteTeacherFromDBWithID(1));
    }

    /**
     * Tests that get all teachers operation executes without throwing exceptions.
     */
    @Test
    void getAll_ShouldWork() {
        assertDoesNotThrow(SQLActions::retrieveTeachersFromDB);
    }

    /**
     * Tests that calculate total salary operation executes without throwing exceptions.
     */
    @Test
    void calculateTotalSalary_ShouldWork() {
        assertDoesNotThrow(() -> teacherController.calculateTotalSalary());
    }

    /**
     * Tests that filter by degree operation executes without throwing exceptions.
     */
    @Test
    void filterByDegree_ShouldWork() {
        assertDoesNotThrow(() -> teacherController.filterByDegree("PhD"));
    }

    /**
     * Tests that extra whitespace in input is handled correctly.
     */
    @Test
    void addTeacherFromTerminal_WithExtraWhitespace_ShouldHandleCorrectly() {
        String inputWithExtraSpaces = "  Jane   Smith   ComputerScience   PhD   50000   jane.smith2t@university.edu   Password123!  ";

        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(inputWithExtraSpaces));
    }

    /**
     * Tests that empty degree filter is handled gracefully.
     */
    @Test
    void filterByDegree_WithEmptyDegree_ShouldHandle() {
        assertDoesNotThrow(() -> teacherController.filterByDegree(""));
    }

    /**
     * Tests that degree filter with invalid characters is handled gracefully.
     */
    @Test
    void filterByDegree_WithInvalidCharacters_ShouldHandle() {
        assertDoesNotThrow(() -> teacherController.filterByDegree("PhD123"));
    }
}
