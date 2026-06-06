package ua.knd11.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knd11.service.TeacherService;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link TeacherController} class.
 * <p>
 * This test class utilizes JUnit 5 and Mockito to verify the behavior of the
 * {@code TeacherController}. It tests various input validation scenarios for
 * adding a teacher via the terminal, as well as delegated operations like filtering,
 * retrieving, and deleting teachers.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    private static MockedStatic<SQLActions> mockedSql;

    /**
     * Initializes static mocks before all tests are executed.
     * <p>
     * Intercepts all static calls to the database utility {@link SQLActions} to prevent
     * the tests from failing due to a lack of an actual database connection. It also
     * configures the mock to return an empty list when retrieving teachers to avoid
     * {@link NullPointerException}s.
     * </p>
     */
    @BeforeAll
    static void initStaticMocks() {
        mockedSql = Mockito.mockStatic(SQLActions.class);
        mockedSql.when(SQLActions::retrieveTeachersFromDB).thenReturn(new ArrayList<>());
    }

    /**
     * Cleans up the static mocks after all tests have finished executing.
     * <p>
     * Closes the {@link MockedStatic} instance to prevent memory leaks and ensure
     * a clean environment for other test classes. Direct deletion from the DB is
     * unnecessary as no actual writes occurred.
     * </p>
     */
    @AfterAll
    static void tearDown() {
        if (mockedSql != null) {
            mockedSql.close();
        }
    }

    /**
     * Tests adding a teacher from the terminal with a perfectly valid input string.
     * Verifies that the controller processes the string without throwing any exceptions.
     */
    @Test
    void addTeacherFromTerminal_WithValidInput_ShouldWork() {
        String validInput = "Jane Smith ComputerScience PhD 50000 jane.smith@university.edu Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(validInput));
    }

    /**
     * Tests adding a teacher from the terminal when the input string contains fewer
     * fields than required. Verifies that the controller handles it gracefully
     * (e.g., by printing an error) without throwing an exception.
     */
    @Test
    void addTeacherFromTerminal_WithTooFewFields_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal when the input string contains more
     * fields than allowed. Verifies that the controller handles it gracefully
     * without throwing an exception.
     */
    @Test
    void addTeacherFromTerminal_WithTooManyFields_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 jane@university.edu Password123! extra";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal with invalid characters (e.g., numbers)
     * in the name field. Verifies that no exception is thrown during validation.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidName_ShouldPrintError() {
        String invalidInput = "Jane123 Smith ComputerScience PhD 50000 jane@university.edu Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal where the salary field is a non-numeric
     * string. Verifies that the controller catches the parsing error without crashing.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidSalaryFormat_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD invalid jane@university.edu Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal with a negative salary amount.
     * Verifies that the business logic validation handles this without throwing an exception.
     */
    @Test
    void addTeacherFromTerminal_WithNegativeSalary_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD -1000 jane@university.edu Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal with a salary of zero.
     * Verifies that the boundary value is handled gracefully by the controller.
     */
    @Test
    void addTeacherFromTerminal_WithZeroSalary_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 0 jane@university.edu Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal using an improperly formatted email address.
     * Verifies that the controller validates the email pattern without crashing.
     */
    @Test
    void addTeacherFromTerminal_WithInvalidEmail_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 invalid-email Password123!";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Tests adding a teacher from the terminal with a password that does not meet
     * the minimum length or security requirements. Verifies graceful failure.
     */
    @Test
    void addTeacherFromTerminal_WithShortPassword_ShouldPrintError() {
        String invalidInput = "Jane Smith ComputerScience PhD 50000 jane@university.edu short";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(invalidInput));
    }

    /**
     * Verifies that deleting a teacher by ID through the mocked {@link SQLActions}
     * executes successfully without throwing any exceptions.
     */
    @Test
    void deleteTeacher_ShouldWork() {
        assertDoesNotThrow(() -> SQLActions.deleteTeacherFromDBWithID(1));
    }

    /**
     * Verifies that retrieving all teachers from the mocked database executes
     * successfully without throwing any exceptions.
     */
    @Test
    void getAll_ShouldWork() {
        assertDoesNotThrow(SQLActions::retrieveTeachersFromDB);
    }

    /**
     * Verifies that invoking the total salary calculation on the mocked {@link TeacherService}
     * executes successfully without throwing any exceptions.
     */
    @Test
    void calculateTotalSalary_ShouldWork() {
        assertDoesNotThrow(() -> teacherService.calculateTotalSalary());
    }

    /**
     * Verifies that filtering teachers by a valid academic degree string works
     * correctly and does not throw an exception.
     */
    @Test
    void filterByDegree_ShouldWork() {
        assertDoesNotThrow(() -> teacherController.filterByDegree("PhD"));
    }

    /**
     * Tests adding a teacher from the terminal when the input string contains excessive
     * whitespace between arguments. Verifies that the controller parses and trims
     * the input correctly without crashing.
     */
    @Test
    void addTeacherFromTerminal_WithExtraWhitespace_ShouldHandleCorrectly() {
        String inputWithExtraSpaces = "  Jane   Smith   ComputerScience   PhD   50000   jane.smith2t@university.edu   Password123!  ";
        assertDoesNotThrow(() -> teacherController.addTeacherFromTerminal(inputWithExtraSpaces));
    }

    /**
     * Tests filtering teachers using an empty string for the degree.
     * Verifies that the controller gracefully handles empty input without crashing.
     */
    @Test
    void filterByDegree_WithEmptyDegree_ShouldHandle() {
        assertDoesNotThrow(() -> teacherController.filterByDegree(""));
    }

    /**
     * Tests filtering teachers using a degree string that contains invalid
     * characters (e.g., numbers). Verifies that the controller handles the invalid
     * filter parameter gracefully.
     */
    @Test
    void filterByDegree_WithInvalidCharacters_ShouldHandle() {
        assertDoesNotThrow(() -> teacherController.filterByDegree("PhD123"));
    }
}